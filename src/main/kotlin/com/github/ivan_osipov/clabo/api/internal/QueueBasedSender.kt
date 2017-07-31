package com.github.ivan_osipov.clabo.api.internal

import com.github.ivan_osipov.clabo.api.model.Message
import com.github.ivan_osipov.clabo.api.output.dto.OutputParams
import com.github.ivan_osipov.clabo.api.output.dto.SendParams
import com.github.ivan_osipov.clabo.utils.ChatId
import com.google.common.base.Preconditions.checkArgument
import com.google.common.collect.Maps
import com.google.common.collect.Queues
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.Semaphore
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.collections.HashMap

internal class QueueBasedSender(val apiInteraction: TelegramApiInteraction) {

    val messageProcessingSemaphore = Semaphore(0)

    private val chatOutputMap: MutableMap<ChatId, ChatInfo> = Maps.newHashMap()

    private val sharedOutputQueue: Queue<OutputParams> = Queues.newLinkedBlockingQueue()

    private val workerExecutor: Executor = Executors.newSingleThreadExecutor() //executes thread for queue processing

    val successCallbacks: MutableMap<OutputParams, (Message) -> Unit> = HashMap()

    private val logger: Logger = LoggerFactory.getLogger(QueueBasedSender::class.java)

    init {
        workerExecutor.execute(WorkerThread())
        logger.debug("QueueBasedSender is initialized")
    }

    fun send(message: SendParams, successCallback: (Message) -> Unit = {}) {
        var chatOutput = chatOutputMap[message.chatId]
        if (chatOutput == null) {
            chatOutput = ChatInfo(message.chatId)
            chatOutputMap[message.chatId] = chatOutput
        }
        chatOutput.successCallbacks.put(message, successCallback)
        chatOutput.outputQueue.add(message)
        logger.debug("New message in queue (chatId: ${chatOutput.chatId})")
        notifyWorker()
    }

    fun send(outputParams: OutputParams, successCallback: (Message) -> Unit = {}) {
        successCallbacks[outputParams] = successCallback
        sharedOutputQueue.add(outputParams)
        logger.debug("New output message")
        notifyWorker()
    }

    inner class WorkerThread : Runnable {

        private val logger: Logger = LoggerFactory.getLogger(WorkerThread::class.java)

        override fun run() {
            while (true) {
                val chatsForProcessing = chatOutputMap.filter { it.value.canBeProcessed() }.values
                if(chatsForProcessing.isEmpty() && sharedOutputQueue.isEmpty()) {
                    messageProcessingSemaphore.acquire()
                    logger.debug("The worker thread is unlocked")
                    continue
                }
                for (chatForProcessing in chatsForProcessing) {

                    val message = chatForProcessing.outputQueue.peek()
                    logger.debug("Message sending")

                    val lockIsSuccessful = chatForProcessing.resultTalkingMutex.compareAndSet(false, true)

                    if (lockIsSuccessful) {
                        val headMessage = chatForProcessing.outputQueue.remove()
                        checkArgument(headMessage == message, "Incorrect message as the queue head")

                        apiInteraction.sendMessage(message, { createdBotMessage ->
                            chatForProcessing.resultTalkingMutex.set(false)
                            notifyWorker()
                            chatForProcessing.successCallbacks[message]?.invoke(createdBotMessage)
                        }, {
                            chatForProcessing.resultTalkingMutex.set(false)
                            notifyWorker()
                        })
                    }
                }
                if(sharedOutputQueue.isNotEmpty()) {
                    while (sharedOutputQueue.isNotEmpty()) {
                        val outputParams = sharedOutputQueue.remove()
                        apiInteraction.sendMessage(outputParams) { createdBotMessage: Message ->
                            successCallbacks[outputParams]?.invoke(createdBotMessage)
                        }
                    }
                }
                Thread.sleep(1)
            }
        }
    }

    private fun notifyWorker() {
        if (messageProcessingSemaphore.hasQueuedThreads()) {
            messageProcessingSemaphore.release()
        }
    }

    private class ChatInfo(val chatId: ChatId,
                   val resultTalkingMutex: AtomicBoolean = AtomicBoolean(false),
                   val outputQueue: Queue<SendParams> = Queues.newLinkedBlockingQueue(),
                   val successCallbacks: MutableMap<SendParams, (Message) -> Unit> = HashMap()) {
        fun canBeProcessed() = !resultTalkingMutex.get() && outputQueue.isNotEmpty()
    }
}