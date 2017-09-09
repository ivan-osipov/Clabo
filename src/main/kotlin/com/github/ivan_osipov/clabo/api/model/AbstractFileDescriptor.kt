package com.github.ivan_osipov.clabo.api.model

import com.google.gson.annotations.SerializedName

open class AbstractFileDescriptor : Identifiable() {

    @SerializedName("file_id")
    override lateinit var id: String

    @SerializedName("file_size")
    var fileSize: Int? = null

}