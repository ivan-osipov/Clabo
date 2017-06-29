package com.github.ivan_osipov.clabo.api.model

import com.google.gson.annotations.SerializedName

class Voice : AbstractFile() {

    @SerializedName("duration")
    private var _duration: Int? = null

    val duration: Int
        get() = _duration!!

    @SerializedName("mime_type")
    var mimeType: Int? = null

}