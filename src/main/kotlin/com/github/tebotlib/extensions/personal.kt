package com.github.tebotlib.extensions

import com.github.tebotlib.Bot

infix inline fun Bot.personal(init: PersonalBotContext.() -> Unit) {
    check(this.apiKey.isNotEmpty())
    check(this.botName.isNotEmpty())

    val context = PersonalBotContext()
    context.init()
}