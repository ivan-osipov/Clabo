package com.github.ivan_osipov.clabo.model.inline_mode.results

import com.google.gson.annotations.SerializedName

class InlineQueryResultVoice : InlineQueryResultSound() {

    @SerializedName("voice_url")
    override lateinit var url: String

    @SerializedName("voice_duration")
    override var duration: Int? = null

}