package com.github.ivan_osipov.clabo.api.output.dto

import com.github.ivan_osipov.clabo.utils.ChatId
import com.github.ivan_osipov.clabo.utils.MessageId

class ForwardMessageParams(
        val chatId: ChatId,
        val fromChatId: ChatId,
        val messageId: MessageId,
        val disableNotification: Boolean = false
) : OutputParams {

    override val queryId: String
        get() = Queries.FORWARD_MESSAGE

    override fun toListOfPairs(): List<Pair<String, *>> {
        return mutableListOf(
                "chat_id" to chatId,
                "from_chat_id" to fromChatId,
                "disable_notification" to disableNotification,
                "message_id" to messageId
        )
    }

}