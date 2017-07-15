package com.github.ivan_osipov.clabo.api.internal

import com.github.ivan_osipov.clabo.dsl.Bot
import com.github.ivan_osipov.clabo.api.input.MessageDto
import com.github.ivan_osipov.clabo.api.input.ResponseDto
import com.github.ivan_osipov.clabo.api.input.UpdatesDto
import com.github.ivan_osipov.clabo.api.input.UserDto
import com.github.ivan_osipov.clabo.api.model.Message
import com.github.ivan_osipov.clabo.api.model.Update
import com.github.ivan_osipov.clabo.api.model.User
import com.github.ivan_osipov.clabo.api.output.dto.AnswerCallbackQueryParams
import com.github.ivan_osipov.clabo.api.output.dto.SendParams
import com.github.ivan_osipov.clabo.api.output.dto.UpdatesParams
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.SocketTimeoutException

internal class TelegramApiInteraction(val bot: Bot) {

    var defaultUpdatesParams = UpdatesParams()

    private val logger: Logger = LoggerFactory.getLogger(TelegramApiInteraction::class.java)

    companion object {
        val GET_ME: String = "getMe"
        val GET_UPDATES: String = "getUpdates"
        val SEND_MESSAGE: String = "sendMessage"
        val ANSWER_CALLBACK_QUERY: String = "answerCallbackQuery"
    }

    fun getMe(callback: (User) -> Unit) {
        invokeGetMethod(GET_ME, UserDto.deserializer, callback)
    }

    fun getUpdates(params: UpdatesParams = defaultUpdatesParams, callback: (List<Update>) -> Unit, errorCallback: (Exception) -> Unit = {}) {
        invokeGetMethod(GET_UPDATES, params.toListOfPairs(), UpdatesDto.deserializer, callback, errorCallback)
    }

    fun sendMessage(sendParams: SendParams) {
        invokePostMethod(SEND_MESSAGE, sendParams.toListOfPairs())
    }

    fun sendMessage(sendParams: AnswerCallbackQueryParams) {
        invokePostMethod(ANSWER_CALLBACK_QUERY, sendParams.toListOfPairs())
    }

    fun sendMessage(sendParams: SendParams, callback: (Message) -> Unit, errorCallback: (Exception) -> Unit) {
        invokePostMethod(SEND_MESSAGE, sendParams.toListOfPairs(), MessageDto.deserializer, callback, errorCallback)
    }

    private fun <T : Any> invokeGetMethod(method: String,
                                          deserializer: ResponseDeserializable<ResponseDto<T>>,
                                          callback: (T) -> Unit = {},
                                          errorCallback: (Exception) -> Unit = {}) {
        invokeGetMethod(method, null, deserializer, callback, errorCallback)
    }

    private fun <T : Any> invokeGetMethod(method: String,
                                          params: List<Pair<String, *>>? = null,
                                          deserializer: ResponseDeserializable<ResponseDto<T>>,
                                          callback: (T) -> Unit = {},
                                          errorCallback: (Exception) -> Unit = {}) {
        method(method).httpGet(params).responseObject(deserializer) { _, _, result ->
            result.fold({ okResult ->
                callback(okResult.result)
            }) { error ->
                errorCallback(error.exception)
                processError(error)
            }
        }
    }

    private fun invokePostMethod(method: String,
                                 params: List<Pair<String, *>>? = null,
                                 callback: () -> Unit = {}) {
        method(method).httpPost(params).response { _, _, result ->
            result.fold({ _ ->
                callback()
            }) { error ->
                processError(error)
            }
        }
    }

    private fun <T : Any> invokePostMethod(method: String,
                                           params: List<Pair<String, *>>? = null,
                                           deserializer: ResponseDeserializable<ResponseDto<T>>,
                                           callback: (T) -> Unit = {},
                                           errorCallback: (Exception) -> Unit) {
        method(method).httpPost(params).responseObject(deserializer) { _, _, result ->
            result.fold({ okResult ->
                callback(okResult.result)
            }) { error ->
                processError(error)
                errorCallback(error)
            }
        }
    }

    private fun processError(error: FuelError) {
        if (error.exception is SocketTimeoutException) {
            logger.debug("Timeout is over")
            return
        }
        if (error.response.httpStatusCode != -1) {
            logger.error("Problems with api. Status code ${error.response.httpStatusCode}.")
            if (error.response.httpStatusCode == 404) {
                logger.error("Check api key")
            }
            if (error.response.httpStatusCode == 409) {
                logger.error("Bot can be already started")
            }
        }
        logger.error("Problem with request", error.exception)
        logger.error(String(error.errorData))
    }

    private fun method(method: String): String {
        return bot.telegramApiUrl + method
    }

}