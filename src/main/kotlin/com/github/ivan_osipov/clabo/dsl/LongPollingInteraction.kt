package com.github.ivan_osipov.clabo.dsl

import com.github.ivan_osipov.clabo.api.internal.TelegramApiInteraction
import com.github.ivan_osipov.clabo.dsl.internal.contextProcessing.ContextProcessor
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class LongPollingInteraction(private val telegramApiUrl: String): Interaction {
    private val logger: Logger = LoggerFactory.getLogger(LongPollingInteraction::class.java)

    override fun run(context: CommonBotContext) {
        val api = TelegramApiInteraction(telegramApiUrl)

        context.sender = api
        context.receiver = api

        api.getMe { me ->
            val botName = me.username ?: "undefined"
            logger.info("Long polling bot: ${me.firstName} ($botName) started")

            val contextProcessor = ContextProcessor(context, api)
            contextProcessor.run()
        }
    }

}