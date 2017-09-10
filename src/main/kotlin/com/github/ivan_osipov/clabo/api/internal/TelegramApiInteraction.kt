package com.github.ivan_osipov.clabo.api.internal

import com.github.ivan_osipov.clabo.api.input.*
import com.github.ivan_osipov.clabo.api.model.*
import com.github.ivan_osipov.clabo.api.output.dto.*
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.httpUpload
import com.github.kittinunf.result.Result
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.SocketTimeoutException
import java.net.UnknownHostException

internal class TelegramApiInteraction(private val baseUrl: String) : IncomingInteractionApi, OutgoingInteractionApi {

    private val logger: Logger = LoggerFactory.getLogger(TelegramApiInteraction::class.java)

    override val defaultUpdatesParams = UpdatesParams()

    override fun getMe(callback: (User) -> Unit) =
            invokeGetAsync(method = Queries.GET_ME, deserializer = UserDto.deserializer, successCallback = callback)

    override fun getMe(): User = invokeGetSync(method = Queries.GET_ME, deserializer = UserDto.deserializer)

    override fun getUpdates(params: UpdatesParams, callback: (List<Update>) -> Unit) =
            invokeGetAsync(Queries.GET_UPDATES, params.toListOfPairs(), UpdatesDto.deserializer, callback)

    override fun getUpdates(params: UpdatesParams) =
            invokeGetSync(Queries.GET_UPDATES, params.toListOfPairs(), UpdatesDto.deserializer)

    override fun sendMessageSync(outputParams: OutputParams): Message {
        return invokePostMethodSync(outputParams.queryId, outputParams.toListOfPairs(), MessageDto.deserializer)
    }

    override fun sendMessageAsync(outputParams: OutputParams, successCallback: (Message) -> Unit) {
        invokePostMethodAsync(outputParams.queryId, outputParams.toListOfPairs(), MessageDto.deserializer, successCallback)
    }

    override fun uploadFileSync(fileParams: SendFileParams): Message {
        val (_, _, result) = method(fileParams.queryId).httpUpload(parameters = fileParams.toListOfPairs())
                .source { _,_ -> fileParams.file!! }
                .name { fileParams.fileType }
                .responseObject(MessageDto.deserializer)
        try {
            processResult(result) { processedResult ->
                //inline
                return processedResult
            }
        } catch (e: RequestAfterException) {
            Thread.sleep(e.timeoutInMillis)
            return uploadFileSync(fileParams)
        }
        //unreachable code in a normal case
        Thread.sleep(1000)
        return uploadFileSync(fileParams)
    }

    override fun uploadFileAsync(fileParams: SendFileParams, successCallback: (Message) -> Unit) {
        val invokeCallback: (Request, Response, Result<MessageDto, FuelError>) -> Unit = { _, _, result ->
            try {
                processResult(result, successCallback)
            } catch (e: RequestAfterException) {
                Thread.sleep(e.timeoutInMillis)
                uploadFileAsync(fileParams, successCallback)
            }
        }
        method(fileParams.queryId).httpUpload(parameters = fileParams.toListOfPairs())
                .source { _,_ -> fileParams.file!! }
                .name { fileParams.fileType }
                .responseObject(MessageDto.deserializer, invokeCallback)
    }

    override fun getChat(params: GetChatParams): Chat {
        return invokeGetSync(Queries.GET_CHAT, params.toListOfPairs(), ChatDto.deserializer)
    }

    override fun getChat(params: GetChatParams, callback: (Chat) -> Unit) {
        invokeGetAsync(Queries.GET_CHAT, params.toListOfPairs(), ChatDto.deserializer, callback)
    }

    private fun <T : Any> invokePostMethodAsync(method: String,
                                                params: List<Pair<String, *>>? = null,
                                                deserializer: ResponseDeserializable<ResponseDto<T>>,
                                                successCallback: (T) -> Unit) {
        val invokeCallback: (Request, Response, Result<ResponseDto<T>, FuelError>) -> Unit = { _, _, result ->
            try {
                processResult(result, successCallback)
            } catch (e: RequestAfterException) {
                Thread.sleep(e.timeoutInMillis)
                invokePostMethodAsync(method, params, deserializer, successCallback)
            }
        }
        invokeHttpMethodAsync(requstForPost(method, params), deserializer, invokeCallback)
    }

    private fun <T : Any> invokePostMethodSync(method: String,
                                               params: List<Pair<String, *>>? = null,
                                               deserializer: ResponseDeserializable<ResponseDto<T>>): T {
        val (_, _, result) = invokeHttpMethodSync(requstForPost(method, params), deserializer)
        try {
            processResult(result) { processedResult ->
                //inline
                return processedResult
            }
        } catch (e: RequestAfterException) {
            Thread.sleep(e.timeoutInMillis)
            return invokePostMethodSync(method, params, deserializer)
        }
        //unreachable code in a normal case
        Thread.sleep(1000)
        return invokePostMethodSync(method, params, deserializer)
    }

    private fun <T : Any> invokeGetAsync(method: String,
                                         params: List<Pair<String, *>>? = null,
                                         deserializer: ResponseDeserializable<ResponseDto<T>>,
                                         successCallback: (T) -> Unit) {
        val invokeCallback: (Request, Response, Result<ResponseDto<T>, FuelError>) -> Unit = { _, _, result ->
            try {
                processResult(result, successCallback)
            } catch (e: RequestAfterException) {
                Thread.sleep(e.timeoutInMillis)
                invokeGetAsync(method, params, deserializer, successCallback)
            }
        }
        invokeHttpMethodAsync(requestForGet(method, params), deserializer, invokeCallback)
    }

    private fun <T : Any> invokeGetSync(method: String,
                                        params: List<Pair<String, *>>? = null,
                                        deserializer: ResponseDeserializable<ResponseDto<T>>): T {
        val (_, _, result) = invokeHttpMethodSync(requestForGet(method, params), deserializer)
        try {
            processResult(result) { processedResult ->
                //inline
                return processedResult
            }
        } catch (e: RequestAfterException) {
            Thread.sleep(e.timeoutInMillis)
            return invokeGetSync(method, params, deserializer)
        }
        //unreachable code in a normal case
        Thread.sleep(1000)
        return invokeGetSync(method, params, deserializer)
    }

     /**
     * @throw RequestAfterException if an invoker should wait some time defined as parameter of exception
     */
    private inline fun <T : Any> processResult(result: Result<ResponseDto<T>, FuelError>,
                                               successCallback: (T) -> Unit) {
        result.fold({ okResult ->
            if (okResult.result !is EmptyMessage) {
                successCallback(okResult.result)
            }
        }) { error ->
            if (error.response.httpStatusCode == 429) {
                val responseStringData = String(error.errorData)
                val responseData = gson.toJsonTree(responseStringData)
                val retryAfter = responseData.asJsonObject["parameters"].asJsonObject["retry_after"] as Long?
                if (retryAfter != null) {
                    logger.warn("High activity! Waiting $retryAfter sec")
                    throw RequestAfterException(retryAfter)
                }
            } else {
                processError(error)
            }
        }
    }

    private fun <T : Any> invokeHttpMethodAsync(httpRequest: Request,
                                                deserializer: ResponseDeserializable<ResponseDto<T>>,
                                                callback: (Request, Response, Result<ResponseDto<T>, FuelError>) -> Unit) {
        httpRequest.responseObject(deserializer, callback)
    }

    private fun <T : Any> invokeHttpMethodSync(httpRequest: Request,
                                               deserializer: ResponseDeserializable<ResponseDto<T>>)
            : Triple<Request, Response, Result<ResponseDto<T>, FuelError>> {
        return httpRequest.responseObject(deserializer)
    }

    private fun requestForGet(method: String, params: List<Pair<String, *>>? = null) = method(method).httpGet(params)

    private fun requstForPost(method: String, params: List<Pair<String, *>>? = null) = method(method).httpPost(params)

    private fun method(method: String): String {
        return baseUrl + method
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
}
