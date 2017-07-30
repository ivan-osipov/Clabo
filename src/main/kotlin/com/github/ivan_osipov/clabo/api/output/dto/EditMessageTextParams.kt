package com.github.ivan_osipov.clabo.api.output.dto

import com.github.ivan_osipov.clabo.api.model.InlineKeyboardMarkup
import com.github.ivan_osipov.clabo.api.model.ParseMode
import com.github.ivan_osipov.clabo.utils.ChatId
import com.github.ivan_osipov.clabo.utils.MessageId

class EditMessageTextParams(val text: String) : OutputParams {

    var chatId: ChatId? = null

    var messageId: MessageId? = null

    var inlineMessageId: String? = null

    var parseMode: ParseMode = ParseMode.NONE

    var disableWebPagePreview: Boolean = false

    var replyMarkup: InlineKeyboardMarkup? = null


    override val queryId: String
        get() = Queries.EDIT_MESSAGE_TEXT

    override fun toListOfPairs(): List<Pair<String, *>> {
        return mutableListOf(
                "chat_id" to chatId,
                "message_id" to messageId,
                "inline_message_id" to inlineMessageId,
                "text" to text,
                "parse_mode" to parseMode,
                "disable_web_page_preview" to disableWebPagePreview,
                "reply_markup" to replyMarkup
        )
    }
}
