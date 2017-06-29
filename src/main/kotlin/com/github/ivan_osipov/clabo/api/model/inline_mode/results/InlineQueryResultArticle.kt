package com.github.ivan_osipov.clabo.api.model.inline_mode.results

import com.github.ivan_osipov.clabo.api.model.inline_mode.contents.InputMessageContent
import com.google.gson.annotations.SerializedName

class InlineQueryResultArticle : InlineQueryResult() {

    @SerializedName("title")
    private var _title: String? = null

    val title: String
        get() = _title!!

    @SerializedName("url")
    var url: String? = null

    @SerializedName("hide_url")
    var hideUrl: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("thumb_url")
    var thumbUrl: String? = null

    @SerializedName("thumb_width")
    var thumbWidth: Int? = null

    @SerializedName("thumb_height")
    var thumbHeight: Int? = null

    @SerializedName("input_message_content")
    private var _inputMessageContent: InputMessageContent? = null

    val inputMessageContent: InputMessageContent
        get() = _inputMessageContent!!

}