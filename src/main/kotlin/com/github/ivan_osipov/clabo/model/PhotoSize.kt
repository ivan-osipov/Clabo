package com.github.ivan_osipov.clabo.model

import com.google.gson.annotations.SerializedName

class PhotoSize : Identifiable() {

    @SerializedName("file_id")
    override lateinit var id: String

    @SerializedName("width")
    private var _width : Int? = null

    @SerializedName("height")
    private var _height : Int? = null

    val width: Int
        get() = _width!!

    val height: Int
        get() = _height!!

    @SerializedName("file_size")
    val fileSize : Long? = null
}