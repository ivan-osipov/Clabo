package com.github.ivan_osipov.clabo.api.internal

import com.github.ivan_osipov.clabo.api.output.dto.SendParams
import com.google.common.collect.Queues
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.Semaphore

internal class QueueBasedSender(val apiInteraction: TelegramApiInteraction) {

    private val outputQueue: Queue<SendParams> = Queues.newConcurrentLinkedQueue()

    private val queueSemaphore: Semaphore = Semaphore(0)

    private val resultTakingSemaphore: Semaphore = Semaphore(1)

    private val workerExecutor: Executor = Executors.newSingleThreadExecutor()

    private val logger: Logger = LoggerFactory.getLogger(QueueBasedSender::class.java)

    init {
        workerExecutor.execute(WorkerThread())
        logger.debug("QueueBasedSender is initialized")
    }

    fun send(sendParams: SendParams) {
        outputQueue.add(sendParams)
        logger.debug("New message in queue")
        queueSemaphore.release()
    }

    inner class WorkerThread : Runnable {

        private val logger: Logger = LoggerFactory.getLogger(WorkerThread::class.java)

        override fun run() {
            while (true) {
                val nextOutputMessage: SendParams? = outputQueue.poll()

                if(nextOutputMessage == null) {
                    logger.debug("Output queue is empty")
                    queueSemaphore.acquire()
                } else {
                    logger.debug("Message sending")
                    resultTakingSemaphore.acquire()

                    apiInteraction.sendMessage(nextOutputMessage, {
                        resultTakingSemaphore.release()
                    }, {
                        resultTakingSemaphore.release()
                    })
                }
            }
        }
    }
}