package com.github.tebotlib

import com.github.tebotlib.auth.AuthContext

open class CommonBotContext(bot: Bot) : AuthContext {

    init {
        check(bot.apiKey.isNotEmpty(), { "Api key is not defined" })
        check(bot.botName.isNotEmpty(), { "Bot name is not loaded (check api key)" })
    }

    fun say(text: String) {
        //todo send text
        println(text)
    }
}