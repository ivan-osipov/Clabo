package com.github.ivan_osipov.clabo.utils

import com.google.common.collect.Multimap

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

operator fun <K, V> Multimap<K, V>.get(key: K): Collection<V> = this.get(key)

operator fun <K, V> Multimap<K, V>.set(key: K, value: V): Boolean = this.put(key, value)