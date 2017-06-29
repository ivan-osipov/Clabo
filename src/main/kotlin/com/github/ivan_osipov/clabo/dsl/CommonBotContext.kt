package com.github.ivan_osipov.clabo.dsl

import com.github.ivan_osipov.clabo.state.chat.StaticChatContext
import com.github.ivan_osipov.clabo.dsl.perks.command.Command
import com.github.ivan_osipov.clabo.dsl.perks.command.CommandsContext
import com.github.ivan_osipov.clabo.dsl.perks.inline.InlineModeContext
import com.github.ivan_osipov.clabo.api.output.dto.SendParams
import com.github.ivan_osipov.clabo.api.model.KeyboardButton
import com.github.ivan_osipov.clabo.api.model.Message
import com.github.ivan_osipov.clabo.api.model.ReplyKeyboardMarkup
import com.github.ivan_osipov.clabo.api.model.Update
import com.github.ivan_osipov.clabo.dsl.config.BotConfigContext
import com.github.ivan_osipov.clabo.utils.ChatId
import com.github.ivan_osipov.clabo.utils.Text
import com.google.common.base.Joiner
import com.google.common.collect.HashMultimap
import org.slf4j.Logger
import org.slf4j.LoggerFactory

open class CommonBotContext(val bot: Bot) {

    var commandsContext = CommandsContext(bot.botName)

    var namedCallbacks = HashMap<String, (Update) -> Unit>()

    var chatContextCallbacks = HashMultimap.create<StaticChatContext, (Message)->Unit>()

    var inlineModeContext = InlineModeContext()

    protected val logger: Logger = LoggerFactory.getLogger(CommonBotContext::class.java)

    init {
        check(bot.apiKey.isNotEmpty(), { "Api key is not defined" })
        check(bot.botName.isNotEmpty(), { "Bot name is not loaded (check api key)" })
    }

    fun configure(init: BotConfigContext.() -> Unit) {
        val config = BotConfigContext()
        config.init()

        bot.api.defaultUpdatesParams = config.updatesParams
    }

    fun helloMessage(text: Text, init: SendParams.() -> Unit = {}) {
        val helloMessageSendParams = SendParams()
        helloMessageSendParams.text = text
        helloMessageSendParams.init()

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

    fun <T : StaticChatContext> inChat(context: T, callback: T.(Message) -> Unit) {
        chatContextCallbacks.put(context, { update ->
            context.callback(update)
        })
    }

    fun say(text: Text, chatId: ChatId) {
        val sendParams = SendParams(chatId, text)
        bot.api.sendMessage(sendParams)
    }

    fun onStartReply(text: Text, init: SendParams.() -> Unit) {
        onStart {
            it.update.message.reply(text, init)
        }
    }

    fun Message?.reply(text: Text, init: SendParams.() -> Unit) {
        val sendParams = SendParams(this!!.chat.id, text)
        sendParams.init()
        bot.api.sendMessage(sendParams)
    }

    fun SendParams.replyKeyboard(init: ReplyKeyboardMarkup.() -> Unit) {
        replyMarkup = ReplyKeyboardMarkup().apply {
            init()
        }
    }

    fun ReplyKeyboardMarkup.line(vararg buttons: KeyboardButton) {
        this.keyboard.add(buttons.toList())
    }

    fun ReplyKeyboardMarkup.oneButtonPerLine(vararg buttons: KeyboardButton) {
        for (button in buttons) {
            this.keyboard.add(listOf(button))
        }
    }

    fun ReplyKeyboardMarkup.button(text: Text) = KeyboardButton().apply {
        this.text = text
    }

    fun ReplyKeyboardMarkup.requestContactButton() = KeyboardButton().apply {
        this.requestContact = true
    }

    fun ReplyKeyboardMarkup.requestLocationButton() = KeyboardButton().apply {
        this.requestLocation = true
    }

    infix fun Message?.answer(text: Text) {
        if(this == null) {
            logger.warn("Trying answer but message is undefined")
        } else {
            val sendParams = SendParams(this.chat.id, text)
            bot.api.sendMessage(sendParams)
        }
    }

}