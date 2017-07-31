package com.github.ivan_osipov.clabo.api.input

import com.github.ivan_osipov.clabo.api.input.deserialization.strategies.AnnotationExclusionStrategy
import com.github.ivan_osipov.clabo.api.model.EmptyMessage
import com.github.ivan_osipov.clabo.api.model.Message
import com.github.ivan_osipov.clabo.api.model.Update
import com.github.ivan_osipov.clabo.api.model.User
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
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

internal val gson: Gson = GsonBuilder().setExclusionStrategies(AnnotationExclusionStrategy()).create()

class UserDto : ResponseDto<User>() {
    object deserializer : ResponseDeserializable<UserDto> {
        override fun deserialize(content: String) = gson.fromJson(content, UserDto::class.java)!!
    }
}

class UpdatesDto : ResponseDto<List<Update>>() {
    object deserializer : ResponseDeserializable<UpdatesDto> {
        override fun deserialize(content: String) = gson.fromJson(content, UpdatesDto::class.java)!!
    }
}

class MessageDto : ResponseDto<Message>() {
    object deserializer : ResponseDeserializable<MessageDto> {
        override fun deserialize(content: String): MessageDto? {
            try {
                return gson.fromJson(content, MessageDto::class.java)!!
            } catch (e: JsonSyntaxException) {
                return MessageDto().apply {
                    result = EmptyMessage
                }
            }
        }
    }
}

fun <T: Any> T?.toJson(): String? = this?.let { gson.toJson(this) }