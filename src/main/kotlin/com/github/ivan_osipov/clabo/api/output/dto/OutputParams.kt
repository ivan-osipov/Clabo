package com.github.ivan_osipov.clabo.api.output.dto

interface OutputParams {
    fun toListOfPairs(): List<Pair<String, *>>
}