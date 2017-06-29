package com.github.ivan_osipov.clabo.dsl

import com.github.ivan_osipov.clabo.api.internal.TelegramApiInteraction
import com.github.ivan_osipov.clabo.dsl.internal.contextProcessing.ContextProcessor
import java.io.FileInputStream
import java.io.InputStream
import java.util.*
import kotlin.reflect.KClass

class Bot internal constructor(){
    lateinit var apiKey: String
    lateinit var botName: String

    internal val api = TelegramApiInteraction(this)
    internal val telegramApiUrl: String by lazy { "https://api.telegram.org/bot$apiKey/" }

    infix fun launch(init: CommonBotContext.() -> Unit) {
        api.getMe { me ->
            this.botName = me.username ?: "undefined"
            println("Personal bot: ${me.firstName} (${this.botName}) started")

            val context = CommonBotContext(this)
            context.init()

            val contextProcessor = ContextProcessor(context)
            contextProcessor.run()
        }
    }
}

fun bot(bot: Bot) = bot

fun bot(apiKey: String): Bot {
    return Bot().apply {
        this.apiKey = apiKey
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
            this.apiKey = props["apiKey"] as String
            check(this.apiKey.isNotEmpty(), { "Check api key" })
        }
    }
}

