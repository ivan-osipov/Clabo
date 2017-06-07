package com.github.ivan_osipov.clabo.model

import com.google.gson.annotations.SerializedName

open class Identifiable {
    @SerializedName("id")
    lateinit var id: String
}