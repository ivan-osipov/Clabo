package com.github.ivan_osipov.clabo.extensions

import com.github.kittinunf.fuel.httpGet
import com.github.ivan_osipov.clabo.Bot
import com.github.ivan_osipov.clabo.dto.UserDto
import com.github.ivan_osipov.clabo.method

infix fun Bot.personal(init: PersonalBotContext.() -> Unit) {
    method("getMe").httpGet().responseObject(UserDto.Deserializer()) { _, _, result ->

        result.fold({ result ->

            this.botName = result.result.username ?: "undefined"
            println("Personal bot: ${result.result.firstName} (${this.botName}) started")

            val context = PersonalBotContext(this)
            context.init()

        }) { error ->
            println("Troubles with connecting ${error.response.httpStatusCode}.")
            if(error.response.httpStatusCode == 404) {
                println("Check api key")
            }
        }
    }
}