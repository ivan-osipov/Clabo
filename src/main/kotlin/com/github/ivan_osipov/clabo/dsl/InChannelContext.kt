package com.github.ivan_osipov.clabo.dsl

import com.github.ivan_osipov.clabo.api.model.Message
import com.github.ivan_osipov.clabo.api.model.Update

class InChannelContext {
    internal val channelMessageCallbacks: MutableList<(Message, Update) -> Unit> = ArrayList()

    fun onMessage(callback: (Message, Update) -> Unit) {
        channelMessageCallbacks.add(callback)
    }
}