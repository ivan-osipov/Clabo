package com.github.ivan_osipov.clabo.api.output.dto

import com.github.ivan_osipov.clabo.api.model.HasEditableReplyMarkup
import com.github.ivan_osipov.clabo.api.model.InlineKeyboardMarkup
import com.github.ivan_osipov.clabo.utils.ChatId
import com.github.ivan_osipov.clabo.utils.MessageId

class EditMessageReplyMarkupParams : OutputParams, HasEditableReplyMarkup<InlineKeyboardMarkup> {

    var chatId: ChatId? = null

    var messageId: MessageId? = null

    var inlineMessageId: MessageId? = null

    override var replyMarkup: InlineKeyboardMarkup? = null

    override val queryId: String
        get() = Queries.EDIT_MESSAGE_REPLY_MARKUP

    override fun toListOfPairs(): List<Pair<String, *>> {
        return listOf(
                "chat_id" to chatId,
                "message_id" to messageId,
                "inline_message_id" to inlineMessageId,
                "reply_markup" to replyMarkup
        )
    }

}