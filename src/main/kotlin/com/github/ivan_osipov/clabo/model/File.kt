package com.github.ivan_osipov.clabo.model

import com.google.gson.annotations.SerializedName

class File : AbstractFile() {

    @SerializedName("file_path")
    var filePath: String? = null

}