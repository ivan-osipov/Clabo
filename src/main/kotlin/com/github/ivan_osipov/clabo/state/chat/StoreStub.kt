package com.github.ivan_osipov.clabo.state.chat

import com.github.ivan_osipov.clabo.utils.ChatId

object StoreStub: ChatStateStore<StaticChatContext> {
    override fun updateContext(chatId: ChatId, chatContext: StaticChatContext) {}

    override fun getChatContext(chatId: ChatId) = StartedChatContext
}