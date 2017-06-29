package com.github.ivan_osipov.clabo.state.chat

import com.github.ivan_osipov.clabo.api.model.Message
import com.google.common.collect.HashMultimap
import com.google.common.collect.Multimap

open class StaticChatContext : ChatContext() {

    val likeMessages: Multimap<String, (Message) -> Unit> = HashMultimap.create()

    fun onOneOfMessages(msg: String, vararg messages: String, callback: (Message) -> Unit) {
        likeMessages.put(msg.toLowerCase(), callback)
        for (message in messages) {
            likeMessages.put(message.toLowerCase(), callback)
        }
    }

}