package com.github.ivan_osipov.clabo.extensions

import com.github.kittinunf.fuel.httpGet
import com.github.ivan_osipov.clabo.Bot
import com.github.ivan_osipov.clabo.url

infix fun Bot.personal(init: PersonalBotContext.() -> Unit) {
    url("getMe").httpGet().responseObject(com.github.clabo.dto.BotInfoDto.Deserializer()) { _, _, result ->

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