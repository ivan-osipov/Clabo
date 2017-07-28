package com.github.ivan_osipov.clabo.api.output.dto

interface SyncByChatsOutputParams {
    fun toListOfPairs(): List<Pair<String, *>>
}