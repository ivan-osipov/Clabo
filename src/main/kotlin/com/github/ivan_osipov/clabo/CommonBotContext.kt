package com.github.ivan_osipov.clabo

import com.github.ivan_osipov.clabo.command.Command
import com.github.ivan_osipov.clabo.command.CommandsContext
import com.github.ivan_osipov.clabo.inline.InlineModeContext
import com.github.ivan_osipov.clabo.internal.apiInteraction.SendParams
import com.github.ivan_osipov.clabo.model.Update
import com.github.ivan_osipov.clabo.settings.BotConfig
import com.github.ivan_osipov.clabo.settings.UpdatesParams
import com.github.ivan_osipov.clabo.utils.ChatId
import com.github.ivan_osipov.clabo.utils.Text
import com.google.common.base.Joiner
import org.slf4j.Logger
import org.slf4j.LoggerFactory

open class CommonBotContext(val bot: Bot) {

    var commandsContext = CommandsContext(bot.botName)

    var namedCallbacks = HashMap<String, (Update) -> Unit>()

    var inlineModeContext = InlineModeContext()

    protected val logger: Logger = LoggerFactory.getLogger(CommonBotContext::class.java)

    init {
        check(bot.apiKey.isNotEmpty(), { "Api key is not defined" })
        check(bot.botName.isNotEmpty(), { "Bot name is not loaded (check api key)" })
    }

    fun configure(init: BotConfig.() -> Unit) {
        val config = BotConfig()
        config.init()
        bot.api.defaultUpdatesParams = config.updatesParams
    }

    fun BotConfig.updates(init: UpdatesParams.() -> Unit) {
        updatesParams.init()
    }

    fun BotConfig.helloMessage(text: Text, init: (SendParams) -> Unit = {}) {
        val helloMessageSendParams = SendParams()
        helloMessageSendParams.text = text
        init(helloMessageSendParams)
        onStart {
            it.update.message?.chat?.id?.let { chatId: ChatId ->
                helloMessageSendParams.chatId = chatId
                bot.api.sendMessage(helloMessageSendParams)
            }
        }
    }

    fun commands(init: CommandsContext.() -> Unit) {
        commandsContext.init()
        logger.info("For comfortable usage your commands you can send to @BotFather follow commands: " +
                Joiner.on(", ").join(commandsContext.commandList.filter { !it.endsWith("@${bot.botName}") }))
    }

    fun onStart(init: (Command) -> Unit) {
        commandsContext.register("start", init)
    }

    fun onHelp(init: (Command) -> Unit) {
        commandsContext.register("help", init)
    }

    fun onSettings(init: (Command) -> Unit) {
        commandsContext.register("settings", init)
    }

    fun addNamedCallback(name: String, callback: ((Update) -> Unit)) {
        namedCallbacks[name] = callback
    }

    fun invokeNamedCallback(name: String, update: Update) {
        namedCallbacks[name]!!(update)
    }

    fun inlineMode(init: InlineModeContext.() -> Unit) {
        logger.info("You have to set inline mode trough @BotFather for providing inline mode")
        inlineModeContext.init()
    }

}