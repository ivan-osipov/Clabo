package com.github.ivan_osipov.clabo.internal.apiInteraction

import com.github.ivan_osipov.clabo.Bot
import com.github.ivan_osipov.clabo.dto.ResponseDto
import com.github.ivan_osipov.clabo.dto.UpdatesDto
import com.github.ivan_osipov.clabo.dto.UserDto
import com.github.ivan_osipov.clabo.model.Update
import com.github.ivan_osipov.clabo.model.User
import com.github.ivan_osipov.clabo.settings.UpdatesParams
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.httpGet

internal class TelegramApiInteraction(val bot: Bot) {

    var defaultUpdatesParams = UpdatesParams()

    companion object {
        val GET_ME : String = "getMe"
        val GET_UPDATES : String = "getUpdates"
    }

    fun getMe(callback: (User) -> Unit) {
        invokeGetMethod(GET_ME, UserDto.deserializer, callback)
    }

    fun getUpdates(params: UpdatesParams = defaultUpdatesParams, callback: (List<Update>) -> Unit) {
        invokeGetMethod(GET_UPDATES, params.toListOfPairs(), UpdatesDto.deserializer, callback)
    }

    private fun <T: Any> invokeGetMethod(method: String,
                                 deserializer: ResponseDeserializable<ResponseDto<T>>,
                                 callback: (T) -> Unit) {
        invokeGetMethod(method, null, deserializer, callback)
    }

    private fun <T: Any> invokeGetMethod(method: String,
                                 params: List<Pair<String, *>>? = null,
                                 deserializer: ResponseDeserializable<ResponseDto<T>>,
                                 callback: (T) -> Unit) {
        method(method).httpGet(params).responseObject(deserializer) { _, _, result ->
            result.fold({ result ->
                callback(result.result)
            }) { error ->
                processError(error)
            }
        }
    }

    private fun processError(error: FuelError) {
        println("Troubles with connecting ${error.response.httpStatusCode}.")
        if (error.response.httpStatusCode == 404) {
            println("Check api key")
        }
        println(error.exception)
    }

    private fun method(method: String): String {
        return bot.telegramApiUrl + method
    }

}