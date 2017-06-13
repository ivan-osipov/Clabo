package com.github.ivan_osipov.clabo.model

import com.google.gson.annotations.SerializedName

class VideoNote : AbstractFile() {

    @SerializedName("length")
    private var _length: Int? = null

    val length: Int
        get() = _length!!

    @SerializedName("duration")
    private var _duration: Int? = null

    val duration: Int
        get() = _duration!!

    @SerializedName("thumb")
    var thumb: PhotoSize? = null

}