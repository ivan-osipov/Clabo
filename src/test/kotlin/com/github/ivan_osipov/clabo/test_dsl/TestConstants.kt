package com.github.ivan_osipov.clabo.test_dsl

import java.util.*

val TEST_API_KEY = "testApiKey"
val TEST_SERVER_PORT = 8585

val testProperties = Properties().apply {
    put("apiKey", TEST_API_KEY)
    put("proxy", "http://localhost:$TEST_SERVER_PORT")
}