package com.github.ivan_osipov.clabo.state.chat

import com.github.ivan_osipov.clabo.api.model.Message
import com.github.ivan_osipov.clabo.api.model.Update
import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.Multimap

open class ChatContext {

    val likeCallbacks: Multimap<String, (Message, Update) -> Unit> = ArrayListMultimap.create()
    val predicateCallbacks: Multimap<(Message) -> Boolean, (Message, Update) -> Unit> = ArrayListMultimap.create()

    fun onOneOfMessages(vararg messages: String, callback: (Message, Update) -> Unit) {
        for (message in messages) {
            likeCallbacks.put(message.toLowerCase(), callback)
        }
    }

    fun onOneOfMessages(vararg predicates: (Message) -> Boolean, callback: (Message, Update) -> Unit) {
        for (predicate in predicates) {
            predicateCallbacks.put(predicate, callback)
        }
    }

}