package com.github.ivan_osipov.clabo.dsl.internal.contextProcessing

import com.github.ivan_osipov.clabo.dsl.CommonBotContext
import com.github.ivan_osipov.clabo.dsl.perks.command.Command
import com.github.ivan_osipov.clabo.api.model.Update
import com.github.ivan_osipov.clabo.utils.isCommand
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.Semaphore

internal class ContextProcessor(val commonBotContext: CommonBotContext) {

    var lastUpdateId: Long = 0
    val logger: Logger = LoggerFactory.getLogger(ContextProcessor::class.java)

    fun run() {
        val bot = commonBotContext.bot
        val api = bot.api
        val commandsContext = commonBotContext.commandsContext
        val inlineModeContext = commonBotContext.inlineModeContext

        val lock: Semaphore = Semaphore(0)

        while (true) {
            try {
                val updateParams = api.defaultUpdatesParams.copy()
                updateParams.offset = lastUpdateId

                api.getUpdates(updateParams, { updates ->
                    val commands = ArrayList<Command>()
                    val inlineQueryUpdates = ArrayList<Update>()

                    //collecting
                    for (update in updates) {
                        refreshLastUpdate(update)

                        val message = update.message
                        if (message != null) {
                            if (message.text.isCommand()) {
                                commands.add(buildCommand(update))
                            }
                        }
                        val inlineQuery = update.inlineQuery
                        if(inlineQuery != null) {
                            inlineQueryUpdates.add(update)
                        }
                    }

                    //processing
                    for (command in commands) {
                        val behavioursForCommand = commandsContext[command.name]
                        for (behaviour in behavioursForCommand) {
                            behaviour(command)
                        }
                    }
                    for (inlineQueryUpdate in inlineQueryUpdates) {
                        inlineModeContext.inlineQueryCallbacks.forEach { callback ->
                            callback(inlineQueryUpdate.inlineQuery!!, inlineQueryUpdate)
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