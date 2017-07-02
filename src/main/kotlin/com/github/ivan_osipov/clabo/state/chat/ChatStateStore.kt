package com.github.ivan_osipov.clabo.state.chat

import com.github.ivan_osipov.clabo.utils.ChatId

interface ChatStateStore<C : ChatContext> {

    fun updateContext(chatId: ChatId, chatContext: C)

    fun getChatContext(chatId: ChatId) : C

}