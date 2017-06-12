package com.github.ivan_osipov.clabo.utils

fun <T : Enum<*>> T.oneOf(vararg possibleTypes: T): Boolean {
    return possibleTypes.contains(this)
}

fun <T : Enum<*>> T.notOneOf(vararg possibleTypes: T): Boolean {
    return !oneOf(*possibleTypes)
}

fun String?.isCommand() : Boolean {
    if(this == null) {
        return false
    }
    return this.startsWith('/') && this.length > 1 && this[1] != ' '
}