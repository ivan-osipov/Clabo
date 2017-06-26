package com.github.ivan_osipov.clabo.inline

import com.github.ivan_osipov.clabo.model.inline_mode.results.InlineQueryResult

class InlineQueryRecognitionResult(val results: List<InlineQueryResult> = ArrayList(),
                                   val isPersonalResults: Boolean = false,
                                   val cacheTime: Long = 300,
                                   val nextOffset: String? = null,
                                   val successfulRecognition: Boolean = false)