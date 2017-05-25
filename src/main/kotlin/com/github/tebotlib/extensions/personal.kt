package com.github.tebotlib.extensions

import com.github.kittinunf.fuel.httpGet
import com.github.tebotlib.Bot
import com.github.tebotlib.dto.BotInfoDto
import com.github.tebotlib.url

infix fun Bot.personal(init: PersonalBotContext.() -> Unit) {
    url("getMe").httpGet().responseObject(BotInfoDto.Deserializer()) { _, _, result ->

        result.fold({ result ->

            this.botName = result.result.username
            println("Personal bot: ${result.result.firstName} (${result.result.username}) started")

            val context = PersonalBotContext(this)
            context.init()

        }) { error ->
            println("Troubles with connecting ${error.message}")
        }
    }
}