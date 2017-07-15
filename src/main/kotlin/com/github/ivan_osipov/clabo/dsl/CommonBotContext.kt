package com.github.ivan_osipov.clabo.dsl

import com.github.ivan_osipov.clabo.api.internal.QueueBasedSender
import com.github.ivan_osipov.clabo.api.model.*
import com.github.ivan_osipov.clabo.api.output.dto.AnswerCallbackQueryParams
import com.github.ivan_osipov.clabo.api.output.dto.SendParams
import com.github.ivan_osipov.clabo.dsl.config.BotConfigContext
import com.github.ivan_osipov.clabo.dsl.perks.command.Command
import com.github.ivan_osipov.clabo.dsl.perks.command.CommandsContext
import com.github.ivan_osipov.clabo.dsl.perks.inline.InlineModeContext
import com.github.ivan_osipov.clabo.state.chat.ChatContext
import com.github.ivan_osipov.clabo.state.chat.ChatInteractionContext
import com.github.ivan_osipov.clabo.state.chat.ChatStateStore
import com.github.ivan_osipov.clabo.utils.ChatId
import com.github.ivan_osipov.clabo.utils.Text
import com.google.common.base.Joiner
import org.slf4j.Logger
import org.slf4j.LoggerFactory

open class CommonBotContext(val bot: Bot) {

    var commandsContext = CommandsContext(bot.botName)

    var namedCallbacks = HashMap<String, (Update) -> Unit>()

    var inlineModeContext = InlineModeContext()

    var chatInteractionContext: ChatInteractionContext<*, *>? = null

    var callbackQueryProcessors = HashMap<String, (CallbackQuery, Update) -> Unit>()

    private val sender = QueueBasedSender(bot.api)

    protected val logger: Logger = LoggerFactory.getLogger(CommonBotContext::class.java)

    init {
        check(bot.apiKey.isNotEmpty(), { "Api key is not defined" })
        check(bot.botName.isNotEmpty(), { "Bot name is not loaded (check api key)" })
    }

    fun <T : ChatStateStore<C>, C : ChatContext> chatting(chatStateStore: T,
                                                          init: ChatInteractionContext<T, C>.() -> Unit) {
        chatInteractionContext = ChatInteractionContext(chatStateStore).apply { init() }
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
                sender.send(helloMessageSendParams)
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

    fun send(text: Text, chatId: ChatId) {
        val sendParams = SendParams(chatId, text)
        sender.send(sendParams)
    }

    fun onStartReply(text: Text, init: SendParams.() -> Unit) {
        onStart {
            it.update.message.reply(text, init)
        }
    }

    fun Message?.reply(text: Text, init: SendParams.() -> Unit) {
        val sendParams = SendParams(this!!.chat.id, text)
        sendParams.init()
        sender.send(sendParams)
    }

    fun SendParams.replyKeyboard(init: ReplyKeyboardMarkup.() -> Unit) {
        replyMarkup = ReplyKeyboardMarkup().apply(init)
    }

    fun SendParams.inlineKeyboard(init: InlineKeyboardMarkup.() -> Unit) {
        replyMarkup = InlineKeyboardMarkup().apply(init)
    }

    fun SendParams.replyKeyboardRemove(selective: Boolean = false) {
        replyMarkup = ReplyKeyboardRemove().apply {
            this.selective = selective
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

    fun InlineKeyboardMarkup.button(text: Text,
                                    callbackData: String,
                                    callbackQueryProcessor: (CallbackQuery, Update) -> Unit = {_,_ -> })
            = abstractButton(text) {
        this.callbackData = callbackData
        callbackQueryProcessors.put(callbackData, callbackQueryProcessor)
    }

    fun InlineKeyboardMarkup.switchInlineQueryButton(text: Text, switchInlineQuery: String) = abstractButton(text) {
        this.switchInlineQuery = switchInlineQuery
    }

    fun InlineKeyboardMarkup.switchInlineQueryCurrentChatButton(text: Text, switchInlineQueryCurrentChat: String)
            = abstractButton(text) {
        this.switchInlineQueryCurrentChat = switchInlineQueryCurrentChat
    }

    fun InlineKeyboardMarkup.callbackGameButton(text: Text, callbackGame: Any) //todo CallbackGame
            = abstractButton(text) {
        this.callbackGame = callbackGame
    }

    fun InlineKeyboardMarkup.payButton(text: Text)
            = abstractButton(text) {
        this.pay = true
    }

    private fun InlineKeyboardMarkup.abstractButton(text: Text, init: InlineKeyboardButton.() -> Unit = {}) = InlineKeyboardButton(text)
            .apply(init)

    fun InlineKeyboardMarkup.line(vararg buttons: InlineKeyboardButton) {
        this.keyboard.add(buttons.toList())
    }

    fun InlineKeyboardMarkup.oneButtonPerLine(vararg buttons: InlineKeyboardButton) {
        for (button in buttons) {
            this.keyboard.add(listOf(button))
        }
    }

    fun ReplyKeyboardMarkup.requestContactButton() = KeyboardButton().apply {
        this.requestContact = true
    }

    fun ReplyKeyboardMarkup.requestLocationButton() = KeyboardButton().apply {
        this.requestLocation = true
    }

    infix fun Message?.answer(text: Text) {
        if (this == null) {
            logger.warn("Trying answer but message is undefined")
        } else {
            val sendParams = SendParams(this.chat.id, text)
            sender.send(sendParams)
        }
    }

    infix fun CallbackQuery.answer(init: AnswerCallbackQueryParams.() -> Unit) {
        val callbackQueryAnswer = makeAnswer().apply(init)
        sender.send(callbackQueryAnswer)
    }

    fun CallbackQuery.makeAnswer(): AnswerCallbackQueryParams {
        return AnswerCallbackQueryParams(this.id)
    }

    infix fun Message?.markdownAnswer(text: Text) {
        if (this == null) {
            logger.warn("Trying answer but message is undefined")
        } else {
            val sendParams = SendParams(this.chat.id, text)
            sendParams.parseMode = ParseMode.MARKDOWN
            sender.send(sendParams)
        }
    }

    infix fun Message?.htmlAnswer(text: Text) {
        if (this == null) {
            logger.warn("Trying answer but message is undefined")
        } else {
            val sendParams = SendParams(this.chat.id, text)
            sendParams.parseMode = ParseMode.HTML
            sender.send(sendParams)
        }
    }

}