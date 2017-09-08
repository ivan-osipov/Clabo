package com.github.ivan_osipov.clabo.api.output.dto

import com.github.ivan_osipov.clabo.utils.ChatId

class GetChatParams(val chatId: ChatId) : OutputParams {
    override val queryId: String
        get() = Queries.GET_CHAT

    override fun toListOfPairs(): List<Pair<String, *>> = listOf("chat_id" to chatId)
}