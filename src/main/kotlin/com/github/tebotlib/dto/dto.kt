package com.github.tebotlib.dto

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.tebotlib.model.BotInfo
import com.google.gson.Gson

open class ResponseDto<T: Any> {
    var ok: Boolean = false
    lateinit var result: T
    val description: String = ""
    val error_code: Int = -1
}

class BotInfoDto : ResponseDto<BotInfo>() {
    class Deserializer: ResponseDeserializable<BotInfoDto> {
        override fun deserialize(content: String) = Gson().fromJson(content, BotInfoDto::class.java)!!
    }
}