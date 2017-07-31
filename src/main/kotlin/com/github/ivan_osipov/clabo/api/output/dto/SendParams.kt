package com.github.ivan_osipov.clabo.api.output.dto

import com.github.ivan_osipov.clabo.api.input.toJson
import com.github.ivan_osipov.clabo.api.model.HasEditableReplyMarkup
import com.github.ivan_osipov.clabo.api.model.ParseMode
import com.github.ivan_osipov.clabo.api.model.ReplyMarkup
import com.google.gson.annotations.SerializedName

class SendParams() : SyncByChatsOutputParams, HasEditableReplyMarkup<ReplyMarkup> {

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

    @SerializedName("parse_mode")
    private var _parseMode: String? = null

    var parseMode: ParseMode
        get() {
            val parseModeCopy = _parseMode
            return if(parseModeCopy == null) ParseMode.NONE else ParseMode.valueOf(parseModeCopy)
        }
        set(value) { _parseMode = value.content }


    var disableWebPagePreview: Boolean = false
    var disableNotification: Boolean = false
    var replyToMessageId: String? = null
    override var replyMarkup: ReplyMarkup? = null

    fun setChatId(chatId: Long) {
        this.chatId = chatId.toString()
    }

    override fun toListOfPairs(): MutableList<Pair<String, *>> {
        return mutableListOf("chat_id" to chatId,
                "text" to text,
                "parse_mode" to _parseMode,
                "disable_web_page_preview" to disableWebPagePreview,
                "disable_notification" to disableNotification,
                "reply_to_message_id" to replyToMessageId,
                "reply_markup" to replyMarkup.toJson())
    }
}