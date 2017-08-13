package com.github.ivan_osipov.clabo.api.internal

import com.github.ivan_osipov.clabo.api.input.*
import com.github.ivan_osipov.clabo.api.model.EmptyMessage
import com.github.ivan_osipov.clabo.api.model.Message
import com.github.ivan_osipov.clabo.api.model.Update
import com.github.ivan_osipov.clabo.api.model.User
import com.github.ivan_osipov.clabo.api.output.dto.OutputParams
import com.github.ivan_osipov.clabo.api.output.dto.Queries
import com.github.ivan_osipov.clabo.api.output.dto.SendParams
import com.github.ivan_osipov.clabo.api.output.dto.UpdatesParams
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.SocketTimeoutException
import java.net.UnknownHostException

internal abstract class TelegramApiInteraction(val baseUrl: String) {

    private val logger: Logger = LoggerFactory.getLogger(TelegramApiInteraction::class.java)

    var defaultUpdatesParams = UpdatesParams()

    abstract fun <T : Any> invokeHttpMethod(httpRequest: Request,
                                            deserializer: ResponseDeserializable<ResponseDto<T>>,
                                            requestCallback: (Request, Response, Result<ResponseDto<T>, FuelError>) -> Unit)

    fun getMe(callback: (User) -> Unit) {
        invokeGetMethod(Queries.GET_ME, UserDto.deserializer, callback)
    }

    fun getUpdates(params: UpdatesParams = defaultUpdatesParams, callback: (List<Update>) -> Unit, errorCallback: (Exception) -> Unit = {}) {
        invokeGetMethod(params.queryId, params.toListOfPairs(), UpdatesDto.deserializer, callback, errorCallback)
    }

    fun sendMessage(outputParams: OutputParams, successCallback: (Message) -> Unit = {}) {
        sendMessage(outputParams, successCallback, {})
    }

    fun sendMessage(outputParams: OutputParams, successCallback: (Message) -> Unit, errorCallback: (Exception) -> Unit) {
        invokePostMethod(outputParams.queryId, outputParams.toListOfPairs(), MessageDto.deserializer, successCallback, errorCallback)
    }

    fun sendMessage(sendParams: SendParams, callback: (Message) -> Unit = {}) {
        sendMessage(sendParams, callback, {})
    }

    fun sendMessage(sendParams: SendParams, callback: (Message) -> Unit, errorCallback: (Exception) -> Unit) {
        invokePostMethod(Queries.SEND_MESSAGE, sendParams.toListOfPairs(), MessageDto.deserializer, callback, errorCallback)
    }

    fun <T : Any> invokeGetMethod(method: String,
                                  deserializer: ResponseDeserializable<ResponseDto<T>>,
                                  callback: (T) -> Unit = {},
                                  errorCallback: (Exception) -> Unit = {}) {
        invokeGetMethod(method, null, deserializer, callback, errorCallback)
    }

    fun <T : Any> invokePostMethod(method: String,
                                   params: List<Pair<String, *>>? = null,
                                   deserializer: ResponseDeserializable<ResponseDto<T>>,
                                   successCallback: (T) -> Unit,
                                   errorCallback: (Exception) -> Unit) {
        invokeHttpMethod({ method(method).httpPost(params) }, deserializer, successCallback, errorCallback)
    }

    fun <T : Any> invokeGetMethod(method: String,
                                  params: List<Pair<String, *>>? = null,
                                  deserializer: ResponseDeserializable<ResponseDto<T>>,
                                  successCallback: (T) -> Unit,
                                  errorCallback: (Exception) -> Unit) {
        invokeHttpMethod({ method(method).httpGet(params) }, deserializer, successCallback, errorCallback)
    }


    fun <T : Any> invokeHttpMethod(methodSupplier: () -> Request,
                                   deserializer: ResponseDeserializable<ResponseDto<T>>,
                                   callback: (T) -> Unit,
                                   errorCallback: (Exception) -> Unit) {
        invokeHttpMethod(methodSupplier(), deserializer) { _, _, result ->
            result.fold({ okResult ->
                if (okResult.result !is EmptyMessage) {
                    callback(okResult.result)
                }
            }) { error ->
                if (error.response.httpStatusCode == 429) {
                    val responseStringData = String(error.errorData)
                    val responseData = gson.toJsonTree(responseStringData)
                    val retryAfter = responseData.asJsonObject["parameters"].asJsonObject["retry_after"] as Int?
                    if (retryAfter != null) {
                        logger.warn("High activity! Waiting $retryAfter sec")
                        Thread.sleep(retryAfter * 1000L)
                        invokeHttpMethod(methodSupplier, deserializer, callback, errorCallback)
                    }
                } else {
                    processError(error)
                    errorCallback(error)
                }
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
            } else if (error.response.httpStatusCode == 409) {
                logger.error("Bot can be already started")
            }
        }
        logger.error("Problem with request", error.exception)
        logger.error(String(error.errorData))
        if (error.exception is UnknownHostException) {
            logger.error("Check network connection")
            System.exit(1)
        }
    }

    private fun method(method: String): String {
        return baseUrl + method
    }
}
