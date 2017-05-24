package com.github.tebotlib

import java.io.FileInputStream
import java.io.InputStream
import java.util.*
import kotlin.reflect.KClass

class Bot internal constructor(){
    lateinit var apiKey: String
    lateinit var botName: String
}

fun bot(bot: Bot) = bot

fun bot(apiKey: String, botName: String): Bot {
    return Bot().apply {
        this.apiKey = apiKey
        this.botName = botName
    }
}

fun properties(filePath: String) = properties(FileInputStream(filePath))

fun properties(kClass: KClass<*>, filePath: String): Bot {
    val resourceStream = kClass::java.get().getResourceAsStream(filePath)
    return properties(resourceStream)
}

fun properties(resourceStream: InputStream): Bot {
    val props = Properties()
    resourceStream.use {
        props.load(it)

        return Bot().apply {
            this.apiKey = props["apiKey"] as String
            this.botName = props["botName"] as String
        }
    }
}