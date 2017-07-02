package com.github.ivan_osipov.clabo.state.chat

import com.github.ivan_osipov.clabo.api.model.Message
import com.github.ivan_osipov.clabo.api.model.Update
import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.Multimap

open class ChatContext {

    val likeMessages: Multimap<String, (Message, Update) -> Unit> = ArrayListMultimap.create()

    fun onOneOfMessages(vararg messages: String, callback: (Message, Update) -> Unit) {
        for (message in messages) {
            likeMessages.put(message.toLowerCase(), callback)
        }
    }

}