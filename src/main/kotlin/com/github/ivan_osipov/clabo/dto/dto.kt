package com.github.ivan_osipov.clabo.dto

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.ivan_osipov.clabo.model.BotInfo
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