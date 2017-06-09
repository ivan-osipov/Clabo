package com.github.ivan_osipov.clabo.utils

fun <T : Enum<*>> T.oneOf(vararg possibleTypes: T): Boolean {
    return possibleTypes.contains(this)
}

fun <T : Enum<*>> T.notOneOf(vararg possibleTypes: T): Boolean {
    return !oneOf(*possibleTypes)
}