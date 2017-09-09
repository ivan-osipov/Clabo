package com.github.ivan_osipov.clabo.api.output.dto

import com.github.ivan_osipov.clabo.api.model.HasEditableInlineKeyboardMarkup
import com.github.ivan_osipov.clabo.api.model.InlineKeyboardMarkup
import com.github.ivan_osipov.clabo.utils.ChatId
import com.github.ivan_osipov.clabo.utils.MessageId

class EditMessageCaptionParams private constructor(override val chatId: ChatId?,
                                                   private val messageId: MessageId?,
                                                   private val inlineMessageId: MessageId?)
    : OutputParams, HasEditableInlineKeyboardMarkup {

    override val queryId: String = Queries.EDIT_MESSAGE_CAPTION

    override var replyMarkup: InlineKeyboardMarkup? = null

    var caption: String? = null

    constructor(chatId: ChatId, messageId: MessageId) : this(chatId, messageId, null)

    constructor(inlineMessageId: MessageId) : this(null, null, inlineMessageId)

    override fun toListOfPairs(): List<Pair<String, *>> {
        return listOf(
                "chat_id" to chatId,
                "message_id" to messageId,
                "inline_message_id" to inlineMessageId,
                "caption" to caption,
                "reply_markup" to replyMarkup
        )
    }

}