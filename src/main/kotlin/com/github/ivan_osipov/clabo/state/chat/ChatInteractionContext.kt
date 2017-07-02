package com.github.ivan_osipov.clabo.state.chat

class ChatInteractionContext <out T: ChatStateStore<C>, C: ChatContext>(val chatStateStore: T) {

    fun <T : StaticChatContext> inContext(context: T, callback: T.() -> Unit) {
        context.callback()
    }

}