package com.github.ivan_osipov.clabo.api.model

import com.google.gson.annotations.SerializedName

class Audio : AbstractFileDescriptor() {

    @SerializedName("duration")
    private var _duration: Int? = null

    val duration: Int
        get() = _duration!!

    @SerializedName("performer")
    var performer: String? = null

    @SerializedName("title")
    var title: String? = null

    @SerializedName("mime_type")
    var mimeType: String? = null

}