package com.github.ivan_osipov.clabo.infrastructure

import com.github.ivan_osipov.clabo.api.input.ResponseDto
import com.github.ivan_osipov.clabo.api.input.toJson

fun <T : Any> T.wrapInResponseDtoJson(): String {
    return ResponseDto<T>().apply {
        ok = true
        result = this@wrapInResponseDtoJson
    }.toJson()!!
}