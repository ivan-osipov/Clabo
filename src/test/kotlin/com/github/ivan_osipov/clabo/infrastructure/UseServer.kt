package com.github.ivan_osipov.clabo.infrastructure

import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class UseServer(
        val telegramServerExtension: KClass<out TelegramServerExtension>
)