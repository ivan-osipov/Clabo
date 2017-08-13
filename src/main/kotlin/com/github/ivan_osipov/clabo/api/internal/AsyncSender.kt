package com.github.ivan_osipov.clabo.api.internal

import com.github.ivan_osipov.clabo.api.model.Message
import com.github.ivan_osipov.clabo.api.output.dto.OutputParams
import com.github.ivan_osipov.clabo.api.output.dto.SendParams
import org.slf4j.Logger
import org.slf4j.LoggerFactory

internal class AsyncSender(val apiInteraction: TelegramApiInteraction) : Sender {

    val logger: Logger = LoggerFactory.getLogger(AsyncSender::class.java)

    override fun send(message: SendParams, successCallback: (Message) -> Unit) {
        apiInteraction.sendMessage(message) { createdBotMessage ->
            logger.trace("Message is sent")
            successCallback(createdBotMessage)
        }
    }

    override fun send(message: OutputParams, successCallback: (Message) -> Unit) {
        apiInteraction.sendMessage(message) { createdBotMessage ->
            logger.trace("Message is sent")
            successCallback(createdBotMessage)
        }
    }

}