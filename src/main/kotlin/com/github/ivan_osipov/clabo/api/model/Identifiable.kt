package com.github.ivan_osipov.clabo.api.model

import com.google.gson.annotations.SerializedName

open class Identifiable {
    @SerializedName("id")
    lateinit open var id: String
}