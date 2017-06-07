package com.github.ivan_osipov.clabo.utils

import com.github.ivan_osipov.clabo.model.Chat

fun Chat.Type.oneOf(vararg possibleTypes: Chat.Type): Boolean {
    return possibleTypes.contains(this)
}

fun Chat.Type.notOneOf(vararg possibleTypes: Chat.Type): Boolean {
    return !oneOf(*possibleTypes)
}