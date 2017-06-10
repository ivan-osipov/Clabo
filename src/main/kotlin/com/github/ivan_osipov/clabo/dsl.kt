package com.github.ivan_osipov.clabo

import com.github.ivan_osipov.clabo.internal.apiInteraction.TelegramApiInteraction
import java.io.FileInputStream
import java.io.InputStream
import java.util.*
import kotlin.reflect.KClass

class Bot internal constructor(){
    lateinit var apiKey: String
    lateinit var botName: String

    internal val api = TelegramApiInteraction(this)
    internal val telegramApiUrl: String by lazy { "https://api.telegram.org/bot$apiKey/" }
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

