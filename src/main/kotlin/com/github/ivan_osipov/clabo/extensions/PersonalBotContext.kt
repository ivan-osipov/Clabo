package com.github.ivan_osipov.clabo.extensions

import com.github.ivan_osipov.clabo.Bot
import com.github.ivan_osipov.clabo.CommonBotContext
import com.github.ivan_osipov.clabo.model.Update

class PersonalBotContext(bot: Bot) : CommonBotContext(bot) {

    fun onUpdates(init: (List<Update>) -> Unit) {
        bot.api.getUpdates {
            init(it)
        }
    }

    fun onUpdate(init: (Update) -> Unit) {
        bot.api.getUpdates { updates ->
            updates.forEach(init)
        }
    }

}