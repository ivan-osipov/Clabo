package com.github.ivan_osipov.clabo.api.model

import com.google.gson.annotations.SerializedName

class Video : AbstractFileDescriptor() {

    @SerializedName("width")
    private var _width: Int? = null

    val width: Int
        get() = _width!!

    @SerializedName("height")
    private var _height: Int? = null

    val height: Int
        get() = _height!!

    @SerializedName("duration")
    private var _duration: Int? = null

    val duration: Int
        get() = _duration!!

    @SerializedName("thumb")
    var thumb: PhotoSize? = null

    @SerializedName("mime_type")
    var mimeType: String? = null

}