package com.github.ivan_osipov.clabo.model.inline_mode.results

import com.google.gson.annotations.SerializedName

class InlineQueryResultPhoto : ViewableInlineQueryResult() {

    @SerializedName("photo_url")
    override lateinit var url: String

    @SerializedName("photo_width")
    override var width: Int? = null

    @SerializedName("photo_height")
    override var height: Int? = null

    @SerializedName("description")
    var description: String? = null

}