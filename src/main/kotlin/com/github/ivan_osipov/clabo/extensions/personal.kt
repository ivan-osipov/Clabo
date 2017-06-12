package com.github.ivan_osipov.clabo.extensions

import com.github.ivan_osipov.clabo.Bot
import com.github.ivan_osipov.clabo.internal.contextProcessing.ContextProcessor

infix fun Bot.personal(init: PersonalBotContext.() -> Unit) {
    api.getMe { me ->
        this.botName = me.username ?: "undefined"
        println("Personal bot: ${me.firstName} (${this.botName}) started")

        val context = PersonalBotContext(this)
        context.init()

        val contextProcessor = ContextProcessor(context)
        contextProcessor.run()
    }
}