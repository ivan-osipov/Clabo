package com.github.ivan_osipov.clabo.api.internal

import com.github.ivan_osipov.clabo.api.input.ResponseDto
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.result.Result

internal class AsyncTelegramApiInteraction(baseUrl: String) : TelegramApiInteraction(baseUrl) {

    override fun <T : Any> invokeHttpMethod(httpRequest: Request,
                                            deserializer: ResponseDeserializable<ResponseDto<T>>,
                                            requestCallback: (Request, Response, Result<ResponseDto<T>, FuelError>) -> Unit) {
        httpRequest.responseObject(deserializer, requestCallback)
    }

}