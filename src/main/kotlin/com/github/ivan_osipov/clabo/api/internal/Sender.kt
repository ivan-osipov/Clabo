package com.github.ivan_osipov.clabo.api.internal

import com.github.ivan_osipov.clabo.api.model.Message
import com.github.ivan_osipov.clabo.api.output.dto.OutputParams
import com.github.ivan_osipov.clabo.api.output.dto.SendParams

interface Sender {
    fun send(message: SendParams, successCallback: (Message) -> Unit = {})

    fun send(message: OutputParams, successCallback: (Message) -> Unit = {})
}