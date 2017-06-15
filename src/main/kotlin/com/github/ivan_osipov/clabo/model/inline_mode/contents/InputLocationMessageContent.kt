package com.github.ivan_osipov.clabo.model.inline_mode.contents

import com.google.gson.annotations.SerializedName

open class InputLocationMessageContent : InputMessageContent() {

    @SerializedName("latitude")
    private var _latitude: Double? = null

    var latitude: Double
        get() = _latitude!!
        set(value) { _latitude = value }

    @SerializedName("longitude")
    private var _longitude: Double? = null

    var longitude: Double
        get() = _longitude!!
        set(value) { _longitude = value }

}