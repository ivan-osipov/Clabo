package com.github.ivan_osipov.clabo.extensions

import com.github.ivan_osipov.clabo.Bot

infix fun Bot.personal(init: PersonalBotContext.() -> Unit) {
    api.getMe { me ->
        this.botName = me.username ?: "undefined"
        println("Personal bot: ${me.firstName} (${this.botName}) started")

        val context = PersonalBotContext(this)
        context.init()
    }
}