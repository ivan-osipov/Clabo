package com.github.ivan_osipov.clabo.model.inline_mode.results

import com.google.gson.annotations.SerializedName

class InlineQueryResultGif : WatchableInlineQueryResult() {

    @SerializedName("gif_duration")
    override var duration: Int? = null

    @SerializedName("gif_url")
    override lateinit var url: String

    @SerializedName("gif_width")
    override var width: Int? = null

    @SerializedName("gif_height")
    override var height: Int? = null

}