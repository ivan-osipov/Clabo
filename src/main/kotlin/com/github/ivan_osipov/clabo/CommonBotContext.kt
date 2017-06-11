package com.github.ivan_osipov.clabo

import com.github.ivan_osipov.clabo.internal.apiInteraction.SendParams
import com.github.ivan_osipov.clabo.model.Message
import com.github.ivan_osipov.clabo.model.Update
import com.github.ivan_osipov.clabo.settings.BotConfig
import com.github.ivan_osipov.clabo.settings.UpdatesParams
import com.github.ivan_osipov.clabo.utils.Text

open class CommonBotContext(val bot: Bot) {

    init {
        check(bot.apiKey.isNotEmpty(), { "Api key is not defined" })
        check(bot.botName.isNotEmpty(), { "Bot name is not loaded (check api key)" })
    }

    fun configure(init: BotConfig.() -> Unit) {
        val config = BotConfig()
        config.init()
        bot.api.defaultUpdatesParams = config.updatesParams
    }

    fun BotConfig.updates(init: UpdatesParams.() -> Unit) {
        updatesParams.init()
    }

    fun BotConfig.helloMessage(text: Text, init: (SendParams) -> Unit = {}) {
        val helloMessageSendParams = SendParams()
        helloMessageSendParams.text = text
        init(helloMessageSendParams)
        this.helloMessage = helloMessageSendParams
    }

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

    fun onMessage(init: (Message) -> Unit) {
        bot.api.getUpdates { updates ->
            updates.asSequence().map { it.message }.filterNotNull().forEach { message ->
                init(message)
            }
        }
    }

}