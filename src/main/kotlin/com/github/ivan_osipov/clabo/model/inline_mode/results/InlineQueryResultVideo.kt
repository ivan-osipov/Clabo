package com.github.ivan_osipov.clabo.model.inline_mode.results

import com.google.gson.annotations.SerializedName

class InlineQueryResultVideo : WatchableInlineQueryResult() {

    @SerializedName("video_duration")
    override var duration: Int? = null

    @SerializedName("video_url")
    override lateinit var url: String

    @SerializedName("video_width")
    override var width: Int? = null

    @SerializedName("video_height")
    override var height: Int? = null

    @SerializedName("mime_type")
    private lateinit var _mimeType: String

    var mimeType: MimeType
        get() = MimeType.valueOf(_mimeType)
        set(value) { _mimeType = value.content }

    @SerializedName("description")
    var description: String? = null

    enum class MimeType(val content: String) {
        TEXT_HTML("text/html"),
        VIDEO_MP4("video/mp4")
    }

}