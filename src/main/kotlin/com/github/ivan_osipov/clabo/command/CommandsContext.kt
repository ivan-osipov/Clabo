package com.github.ivan_osipov.clabo.command

import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.ListMultimap
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class CommandsContext(val botName: String) {

    private val UNKNOWN_COMMAND_KEY = ""
    private val commandMap: ListMultimap<String, (Command) -> Unit> = ArrayListMultimap.create()
    private val logger: Logger = LoggerFactory.getLogger(CommandsContext::class.java)

    fun register(command: String, commandProcessor: (Command) -> Unit) {
        commandMap.put(command, commandProcessor)
        commandMap.put("$command@$botName", commandProcessor)
        logger.info("Command $command is registered")
    }

    fun registerForUnknown(commandProcessor: (Command) -> Unit) {
        register(UNKNOWN_COMMAND_KEY, commandProcessor)
    }

    operator fun get(command: String): List<(Command) -> Unit> {
        val behavioursForCommand = ArrayList(commandMap.get(command))
        if(behavioursForCommand.isEmpty()) {
            behavioursForCommand.addAll(commandMap.get(UNKNOWN_COMMAND_KEY))
        }
        return behavioursForCommand
    }

}