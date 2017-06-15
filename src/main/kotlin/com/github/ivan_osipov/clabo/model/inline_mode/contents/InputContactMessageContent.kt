package com.github.ivan_osipov.clabo.model.inline_mode.contents

import com.google.gson.annotations.SerializedName

class InputContactMessageContent : InputMessageContent() {

    @SerializedName("phone_number")
    lateinit var phoneNumber: String

    @SerializedName("first_name")
    lateinit var firstName: String

    @SerializedName("last_name")
    var lastName: String? = null

}