package com.github.ivan_osipov.clabo.dsl.perks.command

import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.ListMultimap
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class CommandsContext(val botName: String) {

    private val unknownCommandBehaviours: MutableList<(Command) -> Unit> = ArrayList()
    private val commandMap: ListMultimap<String, (Command) -> Unit> = ArrayListMultimap.create()
    private val logger: Logger = LoggerFactory.getLogger(CommandsContext::class.java)
    val commandList: Set<String>
        get() = commandMap.keySet()

    fun register(command: String, commandProcessor: (Command) -> Unit) {
        commandMap.put(command, commandProcessor)
        commandMap.put("$command@$botName", commandProcessor)
        logger.info("Command $command is registered")
    }

    fun registerForUnknown(commandProcessor: (Command) -> Unit) {
        unknownCommandBehaviours.add(commandProcessor)
    }

    operator fun get(command: String): List<(Command) -> Unit> {
        val behavioursForCommand = ArrayList(commandMap.get(command))
        if(behavioursForCommand.isEmpty()) {
            behavioursForCommand.addAll(unknownCommandBehaviours)
        }
        return behavioursForCommand
    }

}