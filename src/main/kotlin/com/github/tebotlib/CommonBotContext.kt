package com.github.tebotlib

import com.github.tebotlib.auth.AuthContext

open class CommonBotContext : AuthContext {
    fun say(text: String) {
        //todo send text
        println(text)
    }
}