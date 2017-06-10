package com.github.ivan_osipov.clabo.internal.apiInteraction

import com.github.ivan_osipov.clabo.model.sending.ReplyMarkup

class SendParams(val chatId: String,
                 val text: String,
                 val parseMode: ParseMode = ParseMode.NONE,
                 val disableWebPagePreview: Boolean = false,
                 val disableNotification: Boolean = false,
                 val replyToMessageId: String? = null,
                 val replyMarkup: ReplyMarkup? = null) {
    constructor(chatId: Long,
                text: String,
                parseMode: ParseMode,
                disableWebPagePreview: Boolean,
                disableNotification: Boolean,
                replyToMessageId: String?,
                replyMarkup: ReplyMarkup?)
            : this(chatId.toString(),
            text,
            parseMode,
            disableWebPagePreview,
            disableNotification,
            replyToMessageId,
            replyMarkup)

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