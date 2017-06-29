package com.github.ivan_osipov.clabo.api.model.inline_mode.results

import com.github.ivan_osipov.clabo.api.model.inline_mode.contents.InputMessageContent
import com.google.gson.annotations.SerializedName

open class ViewableInlineQueryResult : InlineQueryResultWithUrl() {

    open var width: Int? = null

    open var height: Int? = null

    @SerializedName("input_message_content")
    var inputMessageContent: InputMessageContent? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("caption")
    var caption: String? = null

    @SerializedName("thumb_url")
    lateinit var thumbUrl: String
}