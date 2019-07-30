package com.github.ivan_osipov.clabo.dsl

import com.github.ivan_osipov.clabo.api.internal.OutgoingInteractionApi
import com.github.ivan_osipov.clabo.api.internal.TelegramApiInteraction
import org.slf4j.LoggerFactory
import java.io.FileInputStream
import java.io.InputStream
import java.lang.IllegalStateException
import java.util.*
import kotlin.reflect.KClass

class Bot internal constructor(){
    lateinit var apiKey: String
    var host: String = "https://api.telegram.org"
    val logger = LoggerFactory.getLogger(Bot::class.java)

    private val telegramApiUrl: String by lazy { "$host/bot$apiKey/" }

    infix fun longPolling(init: CommonBotContext.() -> Unit) = LongPollingInteraction(telegramApiUrl).execute(init)

    infix fun notification(init: OutgoingInteractionApi.() -> Unit) = TelegramApiInteraction(telegramApiUrl).init()

    private fun Interaction.execute(init: CommonBotContext.() -> Unit) : BotResults {
        val botName = init()
        val context = CommonBotContext(botName)
        context.init()
        return run(context)
    }

    private fun init() : String {
        val apiInteraction = TelegramApiInteraction(telegramApiUrl)
        val user = apiInteraction.getMe()
        val botName = user.username ?: "undefined"
        check(apiKey.isNotEmpty(), { "Api key is not defined" })
        logger.info("Long polling bot: ${user.firstName} ($botName) started")
        return botName
    }
}

fun bot(properties: Properties) : Bot {
    val apiKey = (properties["apiKey"] ?: throw IllegalStateException("apiKey is not defined in properties")) as String
    check(apiKey.isNotEmpty(), { "Check api key" })
    return bot(apiKey, properties["proxy"] as String?)
}

fun bot(apiKey: String, proxy: String? = null): Bot {
    return Bot().apply {
        this.apiKey = apiKey
        proxy?.let {
            this.host = proxy
        }
    }
}

fun props(filePath: String) = props(FileInputStream(filePath))

fun props(kClass: KClass<*>, filePath: String): Properties {
    val resourceStream = kClass::java.get().getResourceAsStream(filePath)
    return props(resourceStream)
}

fun props(resourceStream: InputStream): Properties {
    val props = Properties()
    resourceStream.use {
        props.load(it)
        return props
    }
}

