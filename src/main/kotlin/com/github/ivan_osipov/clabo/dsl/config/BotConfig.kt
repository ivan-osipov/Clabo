package com.github.ivan_osipov.clabo.dsl.config

import com.github.ivan_osipov.clabo.api.output.dto.UpdatesParams

class BotConfig(val updatesParams : UpdatesParams = UpdatesParams(), var async: Boolean = false) {

    fun updates(init: UpdatesParams.() -> Unit) {
        updatesParams.init()
    }

}