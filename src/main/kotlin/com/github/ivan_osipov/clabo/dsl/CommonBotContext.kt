package com.github.ivan_osipov.clabo.dsl

import com.github.ivan_osipov.clabo.api.internal.IncomingInteractionApi
import com.github.ivan_osipov.clabo.api.internal.OutgoingInteractionApi
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
import com.github.ivan_osipov.clabo.utils.FilePointer
import com.github.ivan_osipov.clabo.utils.MessageId
import com.github.ivan_osipov.clabo.utils.Text
import com.google.common.base.Joiner
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.concurrent.atomic.AtomicBoolean

open class CommonBotContext(val botName: String) {

    lateinit var sender: OutgoingInteractionApi

    lateinit var receiver: IncomingInteractionApi

    val configContext = BotConfig()

    var commandsContext = CommandsContext(botName)

    var callbackDataContext = CallbackDataContext()

    var namedCallbacks = HashMap<String, (Update) -> Unit>()

    var inlineModeContext = InlineModeContext()

    var chatInteractionContext: ChatInteractionContext<*, *>? = null

    val stop = AtomicBoolean(false)

    private val logger: Logger = LoggerFactory.getLogger(CommonBotContext::class.java)

    fun <T : ChatStateStore<C>, C : ChatContext> chatting(chatStateStore: T,
                                                          init: ChatInteractionContext<T, C>.() -> Unit) {
        chatInteractionContext = ChatInteractionContext(chatStateStore).apply { init() }
    }

    fun configure(init: BotConfig.() -> Unit) {
        configContext.init()
    }

    fun stop() {
        stop.set(true)
    }

    fun helloMessage(text: Text, init: SendParams.() -> Unit = {}) {
        val helloMessageSendParams = SendParams()
        helloMessageSendParams.text = text
        helloMessageSendParams.init()

        onStart {
            it.update.message?.chat?.id?.let { chatId: ChatId ->
                helloMessageSendParams.chatId = chatId
                sender.sendMessageAsync(helloMessageSendParams)
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
        sender.sendMessageAsync(sendParams, successCallback)
    }

    fun send(text: Text, chatId: ChatId, init: SendParams.() -> Unit = {}) {
        send(text, chatId, init, {})
    }

    fun send(text: Text, chatId: ChatId, init: SendParams.() -> Unit, successCallback: (Message) -> Unit = {}) {
        val sendParams = SendParams(chatId, text)
        sendParams.init()
        sender.sendMessageAsync(sendParams, successCallback)
    }

    //region sendDocument

    fun sendDocument(chatId: ChatId,
                     filePointer: FilePointer,
                     init: SendDocumentParams.() -> Unit = {},
                     successCallback: (Message) -> Unit = {}) {
        val params = SendDocumentParams(chatId, filePointer)
        params.init()
        sender.sendMessageAsync(params, successCallback)
    }

    fun sendDocument(chatId: ChatId,
                     file: java.io.File,
                     init: SendDocumentParams.() -> Unit = {},
                     successCallback: (Message) -> Unit = {}) {
        val params = SendDocumentParams(chatId, file = file)
        params.init()
        sender.uploadFileAsync(params, successCallback)
    }

    fun sendDocumentSync(chatId: ChatId,
                         filePointer: FilePointer? = null,
                         init: SendDocumentParams.() -> Unit = {}): Message {
        val params = SendDocumentParams(chatId, filePointer)
        params.init()
        return sender.sendMessageSync(params)
    }

    fun sendDocumentSync(chatId: ChatId,
                         file: java.io.File,
                         init: SendDocumentParams.() -> Unit = {}): Message {
        val params = SendDocumentParams(chatId, file = file)
        params.init()
        return sender.uploadFileSync(params)
    }

    //endregion

    //region sendPhoto

    fun sendPhoto(chatId: ChatId,
                  filePointer: FilePointer,
                  init: SendPhotoParams.() -> Unit = {},
                  successCallback: (Message) -> Unit = {}) {
        val params = SendPhotoParams(chatId, filePointer)
        params.init()
        sender.sendMessageAsync(params, successCallback)
    }

    fun sendPhoto(chatId: ChatId,
                  file: java.io.File,
                  init: SendPhotoParams.() -> Unit = {},
                  successCallback: (Message) -> Unit = {}) {
        val params = SendPhotoParams(chatId, file = file)
        params.init()
        sender.uploadFileAsync(params, successCallback)
    }

    fun sendPhotoSync(chatId: ChatId,
                      filePointer: FilePointer? = null,
                      init: SendPhotoParams.() -> Unit = {}): Message {
        val params = SendPhotoParams(chatId, filePointer)
        params.init()
        return sender.sendMessageSync(params)
    }

    fun sendPhotoSync(chatId: ChatId,
                      file: java.io.File,
                      init: SendPhotoParams.() -> Unit = {}): Message {
        val params = SendPhotoParams(chatId, file = file)
        params.init()
        return sender.uploadFileSync(params)
    }

    //endregion

    //region sendAudio

    fun sendAudio(chatId: ChatId,
                  filePointer: FilePointer,
                  init: SendAudioParams.() -> Unit = {},
                  successCallback: (Message) -> Unit = {}) {
        val params = SendAudioParams(chatId, filePointer)
        params.init()
        sender.sendMessageAsync(params, successCallback)
    }

    fun sendAudio(chatId: ChatId,
                  file: java.io.File,
                  init: SendAudioParams.() -> Unit = {},
                  successCallback: (Message) -> Unit = {}) {
        val params = SendAudioParams(chatId, file = file)
        params.init()
        sender.uploadFileAsync(params, successCallback)
    }

    fun sendAudioSync(chatId: ChatId,
                      filePointer: FilePointer? = null,
                      init: SendAudioParams.() -> Unit = {}): Message {
        val params = SendAudioParams(chatId, filePointer)
        params.init()
        return sender.sendMessageSync(params)
    }

    fun sendAudioSync(chatId: ChatId,
                      file: java.io.File,
                      init: SendAudioParams.() -> Unit = {}): Message {
        val params = SendAudioParams(chatId, file = file)
        params.init()
        return sender.uploadFileSync(params)
    }

    //endregion

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
        sender.sendMessageAsync(sendParams, successCallback)
    }

    fun Message?.replySync(text: Text, init: SendParams.() -> Unit) : Message {
        val sendParams = SendParams(this!!.chat.id, text)
        sendParams.init()
        return sender.sendMessageSync(sendParams)
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
        if (callbackQueryProcessor != null) {
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

    fun InlineKeyboardMarkup.callbackGameButton(text: Text, callbackGame: CallbackGame)
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
            sender.sendMessageAsync(sendParams)
        }
    }

    infix fun CallbackQuery.answer(init: AnswerCallbackQueryParams.() -> Unit) {
        val callbackQueryAnswer = makeAnswer().apply(init)
        sender.sendMessageAsync(callbackQueryAnswer)
    }

    fun CallbackQuery.emptyAnswer() {
        val callbackQueryAnswer = makeAnswer()
        sender.sendMessageAsync(callbackQueryAnswer)
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
            sender.sendMessageAsync(sendParams)
        }
    }

    infix fun Message?.htmlAnswer(text: Text) {
        if (this == null) {
            logger.warn("Trying answer but message is undefined")
        } else {
            val sendParams = SendParams(this.chat.id, text)
            sendParams.parseMode = ParseMode.HTML
            sender.sendMessageAsync(sendParams)
        }
    }

    fun CommonBotContext.deleteMessage(chatId: ChatId, messageId: MessageId) {
        sender.sendMessageAsync(DeleteMessageParams(chatId, messageId))
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
        sender.sendMessageAsync(editMessageTextParams, successCallback)
    }

    fun Message.editMessageCaptionAtChat(init: EditMessageCaptionParams.() -> Unit) {
        val params = EditMessageCaptionParams(chatId = chat.id, messageId = id)
        params.init()
        sender.sendMessageAsync(params)
    }

    fun Message.editMessageCaptionAsInline(init: EditMessageCaptionParams.() -> Unit) {
        val params = EditMessageCaptionParams(inlineMessageId = id)
        params.init()
        sender.sendMessageAsync(params)
    }

    fun Message.editMessageAsInline(text: Text, init: EditMessageTextParams.() -> Unit) {
        editMessageAsInline(text, init, {})
    }

    fun Message.editMessageAsInline(text: Text, init: EditMessageTextParams.() -> Unit, successCallback: (Message) -> Unit) {
        val editMessageTextParams = EditMessageTextParams(text)
        editMessageTextParams.inlineMessageId = this.id
        editMessageTextParams.init()
        sender.sendMessageAsync(editMessageTextParams, successCallback)
    }

    fun Message.forwardTo(chatId: ChatId, disableNotification: Boolean = false, successCallback: (Message) -> Unit = {}) {
        forwardMessage(chatId, this.chat.id, this.id, disableNotification, successCallback)
    }

    fun forwardMessage(chatId: ChatId, fromChatId: ChatId, messageId: MessageId, disableNotification: Boolean = false, successCallback: (Message) -> Unit = {}) {
        sender.sendMessageAsync(ForwardMessageParams(chatId, fromChatId, messageId, disableNotification), successCallback)
    }

    fun editMessageReplyMarkup(init: EditMessageReplyMarkupParams.() -> Unit, successCallback: (Message) -> Unit = {}) {
        val params = EditMessageReplyMarkupParams()
        params.init()
        sender.sendMessageAsync(params, successCallback)
    }

    fun Message.editMessageReplyMarkup(init: EditMessageReplyMarkupParams.() -> Unit) {
        editMessageReplyMarkup(init, {})
    }

    fun Message.editMessageReplyMarkup(init: EditMessageReplyMarkupParams.() -> Unit, successCallback: (Message) -> Unit = {}) {
        val params = EditMessageReplyMarkupParams()
        params.chatId = this.chat.id
        params.messageId = this.id
        params.init()
        sender.sendMessageAsync(params, successCallback)
    }

    fun ChatId.getChat(callback: (Chat) -> Unit) {
        receiver.getChat(GetChatParams(this), callback)
    }

    fun ChatId.getChat(): Chat = receiver.getChat(GetChatParams(this))

    fun HasEditableReplyMarkup<in InlineKeyboardMarkup>.inlineKeyboard(init: InlineKeyboardMarkup.() -> Unit) {
        replyMarkup = InlineKeyboardMarkup(this).apply(init)
    }

    fun <T : ReplyMarkup> HasEditableReplyMarkup<T>.emptyReplyMarkup() {
        replyMarkup = null
    }

}