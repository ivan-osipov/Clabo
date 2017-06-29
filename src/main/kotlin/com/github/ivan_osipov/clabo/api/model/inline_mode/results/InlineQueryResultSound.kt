package com.github.ivan_osipov.clabo.api.model.inline_mode.results

import com.github.ivan_osipov.clabo.api.model.inline_mode.contents.InputMessageContent
import com.google.gson.annotations.SerializedName

open class InlineQueryResultSound : InlineQueryResultWithUrl() {

    @SerializedName("title")
    lateinit var title: String

    @SerializedName("caption")
    var caption: String? = null

    open var duration: Int? = null //in seconds

    @SerializedName("input_message_content")
    var inputMessageContent: InputMessageContent? = null

}