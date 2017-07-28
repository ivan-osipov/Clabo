package com.github.ivan_osipov.clabo.dsl

import com.github.ivan_osipov.clabo.api.internal.TelegramApiInteraction
import com.github.ivan_osipov.clabo.dsl.internal.contextProcessing.ContextProcessor
import java.io.FileInputStream
import java.io.InputStream
import java.lang.IllegalStateException
import java.util.*
import kotlin.reflect.KClass

class Bot internal constructor(){
    lateinit var apiKey: String
    lateinit var botName: String
    var host: String = "https://api.telegram.org"

    internal val api = TelegramApiInteraction(this)
    internal val telegramApiUrl: String by lazy { "$host/bot$apiKey/" }

    infix fun longPool(init: CommonBotContext.() -> Unit) {
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

