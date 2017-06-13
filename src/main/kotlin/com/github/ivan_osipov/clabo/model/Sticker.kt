package com.github.ivan_osipov.clabo.model

import com.google.gson.annotations.SerializedName

class Sticker : AbstractFile() {

    @SerializedName("width")
    private var _width: Int? = null

    val width: Int
        get() = _width!!

    @SerializedName("height")
    private var _height: Int? = null

    val height: Int
        get() = _height!!

    @SerializedName("thumb")
    var thumb: PhotoSize? = null

    @SerializedName("emoji")
    var emoji: String? = null

}