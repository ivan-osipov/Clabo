package com.github.ivan_osipov.clabo.inline

import com.github.ivan_osipov.clabo.model.inline_mode.InlineQuery

abstract class InlineQueryRecognizer {

    abstract fun recognize(input: InlineQuery) : InlineQueryRecognitionResult
}