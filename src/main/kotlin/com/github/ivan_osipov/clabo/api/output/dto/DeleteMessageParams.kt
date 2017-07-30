package com.github.ivan_osipov.clabo.api.output.dto

import com.github.ivan_osipov.clabo.utils.ChatId
import com.github.ivan_osipov.clabo.utils.MessageId

class DeleteMessageParams(val chatId: ChatId, val messageId: MessageId) : OutputParams {

    override val queryId: String
        get() = Queries.DELETE_MESSAGE

    override fun toListOfPairs(): MutableList<Pair<String, *>> {
        return mutableListOf("chat_id" to chatId,
                "message_id" to messageId
        )
    }
}