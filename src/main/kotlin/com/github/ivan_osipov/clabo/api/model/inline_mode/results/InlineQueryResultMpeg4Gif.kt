package com.github.ivan_osipov.clabo.api.model.inline_mode.results

import com.google.gson.annotations.SerializedName

class InlineQueryResultMpeg4Gif : WatchableInlineQueryResult() {

    @SerializedName("mpeg4_duration")
    override var duration: Int? = null

    @SerializedName("mpeg4_url")
    override lateinit var url: String

    @SerializedName("mpeg4_width")
    override var width: Int? = null

    @SerializedName("mpeg4_height")
    override var height: Int? = null

}