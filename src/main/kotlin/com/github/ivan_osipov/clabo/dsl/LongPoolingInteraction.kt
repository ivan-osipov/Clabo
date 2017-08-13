package com.github.ivan_osipov.clabo.dsl

import com.github.ivan_osipov.clabo.api.internal.AsyncSender
import com.github.ivan_osipov.clabo.api.internal.AsyncTelegramApiInteraction
import com.github.ivan_osipov.clabo.api.internal.SyncTelegramApiInteraction
import com.github.ivan_osipov.clabo.api.internal.TelegramApiInteraction
import com.github.ivan_osipov.clabo.dsl.internal.contextProcessing.ContextProcessor
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class LongPoolingInteraction(val telegramApiUrl: String): Interaction {
    internal lateinit var api: TelegramApiInteraction
    private val logger: Logger = LoggerFactory.getLogger(LongPoolingInteraction::class.java)

    override fun run(context: CommonBotContext) {
        api = if(context.configContext.async) {
            AsyncTelegramApiInteraction(telegramApiUrl)
        } else {
            SyncTelegramApiInteraction(telegramApiUrl)
        }
        context.sender = AsyncSender(api)

        api.getMe { me ->
            val botName = me.username ?: "undefined"
            logger.info("Long pooling bot: ${me.firstName} ($botName) started")

            val contextProcessor = ContextProcessor(context, api)
            contextProcessor.run()
        }
    }

}