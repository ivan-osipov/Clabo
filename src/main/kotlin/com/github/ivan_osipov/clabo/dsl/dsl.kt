package com.github.ivan_osipov.clabo.dsl

import com.github.ivan_osipov.clabo.api.internal.SyncTelegramApiInteraction
import java.io.FileInputStream
import java.io.InputStream
import java.lang.IllegalStateException
import java.util.*
import kotlin.reflect.KClass

class Bot internal constructor(){
    lateinit var apiKey: String
    var host: String = "https://api.telegram.org"

    private val telegramApiUrl: String by lazy { "$host/bot$apiKey/" }

    infix fun longPolling(init: CommonBotContext.() -> Unit)  = LongPollingInteraction(telegramApiUrl).execute(init)

    private fun Interaction.execute(init: CommonBotContext.() -> Unit) {
        val botName = init()
        val context = CommonBotContext(botName)
        context.init()
        run(context)
    }

    private fun init() : String {
        val syncApiInteraction = SyncTelegramApiInteraction(telegramApiUrl)
        val user = syncApiInteraction.getMe()
        val botName = user.username ?: "undefined"
        check(botName.isNotEmpty(), { "Bot name is not found" })
        check(apiKey.isNotEmpty(), { "Api key is not defined" })
        return botName
    }
}

fun bot(bot: Bot) = bot

fun bot(apiKey: String, proxy: String? = null): Bot {
    return Bot().apply {
        this.apiKey = apiKey
        proxy?.let {
            this.host = proxy
        }
    }
}

fun props(filePath: String) = props(FileInputStream(filePath))

fun props(kClass: KClass<*>, filePath: String): Bot {
    val resourceStream = kClass::java.get().getResourceAsStream(filePath)
    return props(resourceStream)
}

fun props(resourceStream: InputStream): Bot {
    val props = Properties()
    resourceStream.use {
        props.load(it)

        return Bot().apply {
            this.apiKey = (props["apiKey"] ?: throw IllegalStateException("apiKey is not defined in properties")) as String
            check(this.apiKey.isNotEmpty(), { "Check api key" })
            props["proxy"]?.let { proxy ->
                this.host = proxy as String
            }

        }
    }
}

