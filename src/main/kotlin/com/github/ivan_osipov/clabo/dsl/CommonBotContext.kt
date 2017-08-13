package com.github.ivan_osipov.clabo.dsl

import com.github.ivan_osipov.clabo.api.internal.Sender
import com.github.ivan_osipov.clabo.api.model.*
import com.github.ivan_osipov.clabo.api.output.dto.*
import com.github.ivan_osipov.clabo.dsl.config.BotConfig
import com.github.ivan_osipov.clabo.dsl.perks.command.Command
import com.github.ivan_osipov.clabo.dsl.perks.command.CommandsContext
import com.github.ivan_osipov.clabo.dsl.perks.inline.InlineModeContext
import com.github.ivan_osipov.clabo.dsl.perks.inlineKeyboard.CallbackDataContext
import com.github.ivan_osipov.clabo.state.chat.ChatContext
import com.github.ivan_osipov.clabo.state.chat.ChatInteractionContext
import com.github.ivan_osipov.clabo.state.chat.ChatStateStore
import com.github.ivan_osipov.clabo.utils.ChatId
import com.github.ivan_osipov.clabo.utils.MessageId
import com.github.ivan_osipov.clabo.utils.Text
import com.google.common.base.Joiner
import org.slf4j.Logger
import org.slf4j.LoggerFactory

open class CommonBotContext(val botName: String) {

    lateinit var sender: Sender

    val configContext = BotConfig()

    var commandsContext = CommandsContext(botName)

    var callbackDataContext = CallbackDataContext()

    var namedCallbacks = HashMap<String, (Update) -> Unit>()

    var inlineModeContext = InlineModeContext()

    var chatInteractionContext: ChatInteractionContext<*, *>? = null

    protected val logger: Logger = LoggerFactory.getLogger(CommonBotContext::class.java)

    fun <T : ChatStateStore<C>, C : ChatContext> chatting(chatStateStore: T,
                                                          init: ChatInteractionContext<T, C>.() -> Unit) {
        chatInteractionContext = ChatInteractionContext(chatStateStore).apply { init() }
    }

    fun configure(init: BotConfig.() -> Unit) {
        configContext.init()
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
                Joiner.on(", ").join(commandsContext.commandList.filter { !it.endsWith("@$botName") }))
    }

    fun callbackData(init: CallbackDataContext.() -> Unit) {
        callbackDataContext.init()
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

    fun send(sendParams: SendParams, successCallback: (Message) -> Unit = {}) {
        sender.send(sendParams, successCallback)
    }

    fun send(text: Text, chatId: ChatId, init: SendParams.() -> Unit = {}) {
        send(text, chatId, init, {})
    }

    fun send(text: Text, chatId: ChatId, init: SendParams.() -> Unit, successCallback: (Message) -> Unit = {}) {
        val sendParams = SendParams(chatId, text)
        sendParams.init()
        sender.send(sendParams, successCallback)
    }

    fun onStartReply(text: Text, init: SendParams.() -> Unit) {
        onStart {
            it.update.message.reply(text, init)
        }
    }

    infix fun Message?.reply(text: Text) {
        reply(text, {})
    }

    fun Message?.reply(text: Text, init: SendParams.() -> Unit) {
        reply(text, init, {})
    }

    fun Message?.reply(text: Text, init: SendParams.() -> Unit, successCallback: (Message) -> Unit = {}) {
        val sendParams = SendParams(this!!.chat.id, text)
        sendParams.init()
        sender.send(sendParams, successCallback)
    }

    fun SendParams.replyKeyboard(init: ReplyKeyboardMarkup.() -> Unit) {
        replyMarkup = ReplyKeyboardMarkup(this).apply(init)
    }

    fun SendParams.replyKeyboardRemove(selective: Boolean = false) {
        replyMarkup = ReplyKeyboardRemove(this).apply {
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
                                    callbackQueryProcessor: ((CallbackQuery, Update) -> Unit)? = null)
            = abstractButton(text) {
        this.callbackData = callbackData
        if(callbackQueryProcessor != null) {
            callbackDataContext.register(callbackData, callbackQueryProcessor)
        }
    }

    fun InlineKeyboardMarkup.urlButton(text: Text,
                                    url: String)
            = abstractButton(text) {
        this.url = url
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

    fun CallbackQuery.emptyAnswer() {
        val callbackQueryAnswer = makeAnswer()
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

    fun CommonBotContext.deleteMessage(chatId: ChatId, messageId: MessageId) {
        sender.send(DeleteMessageParams(chatId, messageId))
    }

    fun Message.deleteMessage() {
        deleteMessage(this.chat.id, this.id)
    }

    fun Message.editMessageAtChat(text: Text, init: EditMessageTextParams.() -> Unit) {
        editMessageAtChat(text, init, {})
    }

    fun Message.editMessageAtChat(text: Text, init: EditMessageTextParams.() -> Unit, successCallback: (Message) -> Unit) {
        val editMessageTextParams = EditMessageTextParams(text)
        editMessageTextParams.chatId = this.chat.id
        editMessageTextParams.messageId = this.id
        editMessageTextParams.init()
        sender.send(editMessageTextParams, successCallback)
    }

    fun Message.editMessageAsInline(text: Text, inlineMessageId: MessageId, init: EditMessageTextParams.() -> Unit) {
        editMessageAsInline(text, inlineMessageId, init, {})
    }

    fun Message.editMessageAsInline(text: Text, inlineMessageId: MessageId, init: EditMessageTextParams.() -> Unit, successCallback: (Message) -> Unit) {
        val editMessageTextParams = EditMessageTextParams(text)
        editMessageTextParams.inlineMessageId = inlineMessageId
        editMessageTextParams.init()
        sender.send(editMessageTextParams, successCallback)
    }

    fun Message.forwardTo(chatId: ChatId, disableNotification: Boolean = false, successCallback: (Message) -> Unit = {}) {
        forwardMessage(chatId, this.chat.id, this.id, disableNotification, successCallback)
    }

    fun forwardMessage(chatId: ChatId, fromChatId: ChatId, messageId: MessageId, disableNotification: Boolean = false, successCallback: (Message) -> Unit = {}) {
        sender.send(ForwardMessageParams(chatId, fromChatId, messageId, disableNotification), successCallback)
    }

    fun editMessageReplyMarkup(init: EditMessageReplyMarkupParams.() -> Unit, successCallback: (Message) -> Unit = {}) {
        val params = EditMessageReplyMarkupParams()
        sender.send(params, successCallback)
    }

    fun Message.editMessageReplyMarkup(init: EditMessageReplyMarkupParams.() -> Unit) {
        editMessageReplyMarkup(init, {})
    }

    fun Message.editMessageReplyMarkup(init: EditMessageReplyMarkupParams.() -> Unit, successCallback: (Message) -> Unit = {}) {
        val params = EditMessageReplyMarkupParams()
        params.chatId = this.chat.id
        params.messageId = this.id
        params.init()
        sender.send(params, successCallback)
    }

    fun HasEditableReplyMarkup<in InlineKeyboardMarkup>.inlineKeyboard(init: InlineKeyboardMarkup.() -> Unit) {
        replyMarkup = InlineKeyboardMarkup(this).apply(init)
    }

    fun <T: ReplyMarkup> HasEditableReplyMarkup<T>.emptyReplyMarkup() {
        replyMarkup = null
    }

}