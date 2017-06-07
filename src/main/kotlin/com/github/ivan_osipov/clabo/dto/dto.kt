package com.github.ivan_osipov.clabo.dto

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.ivan_osipov.clabo.model.User
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

open class ResponseDto<T: Any> {
    var ok: Boolean = false
    lateinit var result: T
    var description: String? = null
    var error_code: Int = -1
    var parameters: ResponseParameters? = null
}

class ResponseParameters {
    @SerializedName("migrate_to_chat_id")
    var migrateToChatId: Long? = null

    @SerializedName("retry_after")
    var retryAfter: Int? = null
}

class UserDto : ResponseDto<User>() {
    class Deserializer: ResponseDeserializable<UserDto> {
        override fun deserialize(content: String) = Gson().fromJson(content, UserDto::class.java)!!
    }
}