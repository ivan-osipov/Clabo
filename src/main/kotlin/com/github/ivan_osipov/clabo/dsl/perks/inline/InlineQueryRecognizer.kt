package com.github.ivan_osipov.clabo.dsl.perks.inline

import com.github.ivan_osipov.clabo.api.model.inline_mode.InlineQuery

abstract class InlineQueryRecognizer {

    abstract fun recognize(input: InlineQuery) : InlineQueryRecognitionResult
}