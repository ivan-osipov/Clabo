package com.github.ivan_osipov.clabo.infrastructure

import spark.Service

@FunctionalInterface
interface TelegramServerExtension {

    fun Service.setup()

}