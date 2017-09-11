package com.github.ivan_osipov.clabo

import com.github.ivan_osipov.clabo.api.input.ResponseDto
import com.github.ivan_osipov.clabo.api.input.toJson
import com.github.ivan_osipov.clabo.dsl.BotResults
import com.github.ivan_osipov.clabo.dsl.bot
import com.github.ivan_osipov.clabo.test_dsl.*
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.Matcher
import org.junit.Assert.assertThat
import org.junit.Test
import spark.Service

class SystemBotCommandTests {

    @Test
    fun `stop command is working`() {
        Service.ignite().apply {
            port(TEST_SERVER_PORT)
            get("/bot$TEST_API_KEY/getMe") { _, _ ->
                user {
                    firstName = "testBot"
                    isBot = true
                }.wrapInResponseDtoJson()
            }
            get("/bot$TEST_API_KEY/getUpdates") { _, _ ->
                listOf(
                        update {
                            message {
                                text = "/start"
                            }
                        }
                ).wrapInResponseDtoJson()
            }
        }

        bot(testProperties) longPolling {
            onStart {
                it.message answer "Hi!"
                stop()
            }
        } assertThatBotStopReason isNull

    }

    infix fun BotResults.assertThatBotStopReason(matcher: Matcher<in Any?>) {
        assertThat(stopReason.toString(), stopReason, matcher)
    }

    val isNull: Matcher<Any?> = nullValue()

    fun <T : Any> T.wrapInResponseDtoJson(): String {
        return ResponseDto<T>().apply {
            ok = true
            result = this@wrapInResponseDtoJson
        }.toJson()!!
    }
}