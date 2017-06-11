package com.github.ivan_osipov.clabo.internal.apiInteraction

import com.github.ivan_osipov.clabo.model.sending.ReplyMarkup

class SendParams() {

    constructor(chatId: String, text: String) : this() {
        this.chatId = chatId
        this.text = text
    }

    constructor(chatId: Long, text: String) : this(chatId.toString(), text)

    lateinit var chatId: String
    var chatIdAsLong
        get() = chatId.toLong()
        set(value) {
            chatId = value.toString()
        }
    lateinit var text: String
    var parseMode: ParseMode = ParseMode.NONE
    var disableWebPagePreview: Boolean = false
    var disableNotification: Boolean = false
    var replyToMessageId: String? = null
    var replyMarkup: ReplyMarkup? = null

    fun setChatId(chatId: Long) {
        this.chatId = chatId.toString()
    }

    fun toListOfPairs(): List<Pair<String, *>> {
        return listOf("chat_id" to chatId,
                "text" to text,
                "parse_mode" to parseMode.name,
                "disable_web_page_preview" to disableWebPagePreview,
                "disable_notification" to disableNotification,
                "reply_to_message_id" to replyToMessageId,
                "reply_markup" to replyMarkup)
    }
}