package com.github.ivan_osipov.clabo.api.model

import com.google.gson.annotations.SerializedName

class Audio : Identifiable() {

    @SerializedName("file_id")
    override lateinit var id: String

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

    @SerializedName("file_size")
    var fileSize: Int? = null

}