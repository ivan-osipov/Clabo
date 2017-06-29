package com.github.ivan_osipov.clabo.api.model

import com.google.gson.annotations.SerializedName

class Document : AbstractFile() {

    @SerializedName("thumb")
    var thumb: PhotoSize? = null

    @SerializedName("file_name")
    var fileName: String? = null

    @SerializedName("mime_type")
    var mimeType: String? = null

}