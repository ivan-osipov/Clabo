package com.github.ivan_osipov.clabo.model.inline_mode.results

import com.google.gson.annotations.SerializedName

class InlineQueryResultAudio : InlineQueryResultSound() {

    @SerializedName("audio_url")
    override lateinit var url: String

    @SerializedName("audio_duration")
    override var duration: Int? = null

    @SerializedName("performer")
    var performer: String? = null

}