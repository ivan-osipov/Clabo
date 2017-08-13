package com.github.ivan_osipov.clabo.dsl.internal.contextProcessing

import com.github.ivan_osipov.clabo.api.internal.TelegramApiInteraction
import com.github.ivan_osipov.clabo.api.model.Message
import com.github.ivan_osipov.clabo.dsl.CommonBotContext
import com.github.ivan_osipov.clabo.dsl.perks.command.Command
import com.github.ivan_osipov.clabo.api.model.Update
import com.github.ivan_osipov.clabo.state.chat.ChatContext
import com.github.ivan_osipov.clabo.state.chat.ChatStateStore
import com.github.ivan_osipov.clabo.utils.isCommand
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.Semaphore

internal class ContextProcessor(val commonBotContext: CommonBotContext, private val api: TelegramApiInteraction) {

    var lastUpdateId: Long = 0
    val logger: Logger = LoggerFactory.getLogger(ContextProcessor::class.java)

    fun run() {
        val lock: Semaphore = Semaphore(0)

        while (true) {
            try {
                val updateParams = api.defaultUpdatesParams.copy()

                updateParams.offset = lastUpdateId

                api.getUpdates(updateParams, { updates ->

                    val executionBatch = collectExecutionBatch(updates)

                    processExecutionBatch(executionBatch)

                    lock.release()
                }, {
                    lock.release()
                })

                lock.acquire()
            } catch (e: Exception) {
                logger.error("Getting updates error", e)
                lock.release()
            } finally {
                Thread.sleep(1)
            }
        }
    }

    private fun collectExecutionBatch(updates: List<Update>): ExecutionBatch {
        val commandsContext = commonBotContext.commandsContext
        val inlineModeContext = commonBotContext.inlineModeContext
        val chatInteractionContext = commonBotContext.chatInteractionContext
        val chatStateStore: ChatStateStore<*>? = chatInteractionContext?.chatStateStore
        val callbackDataRegister = commonBotContext.callbackDataContext.register

        val executionBatch = ExecutionBatch()
        for (update in updates) {
            refreshLastUpdate(update)

            val message = update.message
            if (message != null) {
                if (message.text.isCommand()) {

                    val command = buildCommand(update)
                    val behavioursForCommand = commandsContext[command.name]

                    executionBatch.callbacks.add {
                        for (behaviour in behavioursForCommand) {
                            behaviour(command)
                        }
                    }

                } else {
                    val chatContext: ChatContext? = chatStateStore?.getChatContext(message.chat.id)
                    chatContext?.let {
                        val messagesProcessors: Collection<(Message, Update) -> Unit> = chatContext
                                .likeCallbacks.get(message.text?.toLowerCase())
                        val predicateCallbacks = chatContext.predicateCallbacks.asMap()

                        for (messagesProcessor in messagesProcessors) {
                            executionBatch.callbacks.add {
                                messagesProcessor(message, update)
                            }
                        }

                        predicateCallbacks.entries.forEach { entry ->
                            val predicate = entry.key
                            if (predicate(message)) {
                                executionBatch.callbacks.add {
                                    for (callback in entry.value) {
                                        callback(message, update)
                                    }
                                }
                            }
                        }
                    }
                }
            }
            val inlineQuery = update.inlineQuery
            if (inlineQuery != null) {
                inlineModeContext.inlineQueryCallbacks.forEach { callback ->
                    executionBatch.callbacks.add {
                        callback(inlineQuery, update)
                    }
                }
            }
            val callbackQuery = update.callbackQuery
            if (callbackQuery != null) {
                val callbackQueryData = callbackQuery.data
                if (callbackQueryData != null) {
                    val chatId = callbackQuery.message?.chat?.id
                    logger.trace("Processing callbackData: $callbackQueryData for $chatId")
                    callbackDataRegister[callbackQueryData]?.let { callbackDaraProcessors ->
                        logger.trace("For $callbackQueryData found ${callbackDaraProcessors.size} processors")
                        for (callbackDaraProcessor in callbackDaraProcessors) {
                            executionBatch.callbacks.add {
                                callbackDaraProcessor(callbackQuery, update)
                            }
                        }
                    }
                }
            }
        }
        return executionBatch
    }

    private fun processExecutionBatch(executionBatch: ExecutionBatch) {
        for (callback in executionBatch.callbacks) {
            callback()
        }
    }

    private fun refreshLastUpdate(update: Update) {
        lastUpdateId = Math.max(lastUpdateId, update.id.toLong() + 1)
    }

    private fun buildCommand(update: Update): Command {
        val text = update.message!!.text!!
        val parts: List<String> = text.split(" ")
        var name: String = parts[0].toLowerCase()
        val parameter: String? = if (parts.size > 1) parts[1] else null
        name = name.substring(1)
        val commandObj = Command(name, parameter, update)

        return commandObj
    }

}