package com.github.ivan_osipov.clabo.model

import com.google.gson.annotations.SerializedName

class BotInfo: Identifiable() {
    @SerializedName("first_name")
    lateinit var firstName: String
    lateinit var username: String
}