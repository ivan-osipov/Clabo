package com.github.ivan_osipov.clabo.api.internal

import com.github.ivan_osipov.clabo.api.model.Message
import com.github.ivan_osipov.clabo.api.output.dto.OutputParams
import com.github.ivan_osipov.clabo.api.output.dto.SendParams

internal class AsyncQueueBasedSender(val apiInteraction: TelegramApiInteraction) : Sender {
    override fun send(message: SendParams, successCallback: (Message) -> Unit) {
        apiInteraction.sendMessage(message) { createdBotMessage ->
            successCallback(createdBotMessage)
        }
    }

    override fun send(message: OutputParams, successCallback: (Message) -> Unit) {
        apiInteraction.sendMessage(message) { createdBotMessage ->
            successCallback(createdBotMessage)
        }
    }

}