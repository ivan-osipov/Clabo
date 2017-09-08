package com.github.ivan_osipov.clabo.api.model

import com.github.ivan_osipov.clabo.utils.FileId
import com.google.gson.annotations.SerializedName

class ChatPhoto {
    @SerializedName("small_file_id")
    lateinit var smallFileId: FileId

    @SerializedName("big_file_id")
    lateinit var bigFileId: FileId
}