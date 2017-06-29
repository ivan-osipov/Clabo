package com.github.ivan_osipov.clabo.api.model

import com.google.gson.annotations.SerializedName

class KeyboardButton {

    @SerializedName("text")
    lateinit var text: String

    @SerializedName("request_contact")
    var requestContact: Boolean = false

    @SerializedName("request_location")
    var requestLocation: Boolean = false

}