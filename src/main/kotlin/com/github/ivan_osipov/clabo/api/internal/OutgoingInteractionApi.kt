package com.github.ivan_osipov.clabo.api.internal

import com.github.ivan_osipov.clabo.api.model.Message
import com.github.ivan_osipov.clabo.api.output.dto.OutputParams
import com.github.ivan_osipov.clabo.api.output.dto.SendParams

interface OutgoingInteractionApi {

    fun sendMessageSync(outputParams: OutputParams) : Message

    fun sendMessageAsync(outputParams: OutputParams, successCallback: (Message) -> Unit = {})

    fun sendMessageSync(sendParams: SendParams) : Message

    fun sendMessageAsync(sendParams: SendParams, callback: (Message) -> Unit = {})

}