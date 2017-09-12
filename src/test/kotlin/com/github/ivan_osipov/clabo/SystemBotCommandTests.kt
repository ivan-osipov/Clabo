package com.github.ivan_osipov.clabo

import com.github.ivan_osipov.clabo.dsl.bot
import com.github.ivan_osipov.clabo.infrastructure.ClaboTest
import com.github.ivan_osipov.clabo.infrastructure.TelegramServerExtension
import com.github.ivan_osipov.clabo.infrastructure.UseServer
import com.github.ivan_osipov.clabo.infrastructure.dsl.*
import com.github.ivan_osipov.clabo.infrastructure.wrapInResponseDtoJson
import org.junit.jupiter.api.Test
import spark.Service

@ClaboTest
@UseServer(SystemBotCommandTests.TestTelegramServerExtension::class)
class SystemBotCommandTests {

    class TestTelegramServerExtension : TelegramServerExtension {
        override fun Service.setup() {
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
    }

    @Test
    fun `stop command is working`() {
        bot(testProperties) longPolling {
            onStart {
                it.message answer "Hi!"
                stop()
            }
        } assertThatBotStopReason isNull
    }
}