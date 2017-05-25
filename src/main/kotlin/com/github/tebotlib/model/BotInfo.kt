package com.github.tebotlib.model

import com.google.gson.annotations.SerializedName

class BotInfo: Identifiable() {
    @SerializedName("first_name")
    lateinit var firstName: String
    lateinit var username: String
}