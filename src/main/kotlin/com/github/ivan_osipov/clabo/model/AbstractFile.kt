package com.github.ivan_osipov.clabo.model

import com.google.gson.annotations.SerializedName

open class AbstractFile : Identifiable() {

    @SerializedName("file_id")
    override lateinit var id: String

    @SerializedName("file_size")
    var fileSize: Int? = null

}