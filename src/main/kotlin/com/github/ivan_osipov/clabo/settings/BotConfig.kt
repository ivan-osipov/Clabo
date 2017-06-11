package com.github.ivan_osipov.clabo.settings

import com.github.ivan_osipov.clabo.internal.apiInteraction.SendParams

class BotConfig(var helloMessage: SendParams? = null, val updatesParams : UpdatesParams = UpdatesParams())