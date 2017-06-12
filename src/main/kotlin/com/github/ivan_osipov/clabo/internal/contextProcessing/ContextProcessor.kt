package com.github.ivan_osipov.clabo.internal.contextProcessing

import com.github.ivan_osipov.clabo.CommonBotContext
import com.github.ivan_osipov.clabo.command.Command
import com.github.ivan_osipov.clabo.model.Update
import com.github.ivan_osipov.clabo.utils.isCommand
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.Semaphore

class ContextProcessor(val commonBotContext: CommonBotContext) {

    var lastUpdateId: Long = 0
    val logger: Logger = LoggerFactory.getLogger(ContextProcessor::class.java)

    fun run() {
        val bot = commonBotContext.bot
        val api = bot.api
        val commandsContext = commonBotContext.commandsContext

        val lock: Semaphore = Semaphore(0)

        while (true) {
            try {
                val updateParams = api.defaultUpdatesParams.copy()
                updateParams.offset = lastUpdateId

                api.getUpdates(updateParams, { updates ->
                    val commands = ArrayList<Command>()

                    //collecting
                    for (update in updates) {
                        val message = update.message
                        if (message != null) {
                            updateLastUpdateIdForChat(update)

                            if (message.text.isCommand()) {
                                commands.add(buildCommand(update))
                            }
                        }
                    }

                    //processing
                    for (command in commands) {
                        val behavioursForCommand = commandsContext[command.name]
                        for (behaviour in behavioursForCommand) {
                            behaviour(command)
                        }
                    }

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

    private fun updateLastUpdateIdForChat(update: Update) {
        lastUpdateId = Math.max(lastUpdateId, update.id.toLong() + 1)
    }

    private fun buildCommand(update: Update): Command {
        val text = update.message!!.text!!
        val parts: List<String> = text.split(" ")
        var name: String = parts[0]
        val parameter: String? = if (parts.size > 1) parts[1] else null
        name = name.substring(1)
        val commandObj = Command(name, parameter, update)
        return commandObj
    }

}