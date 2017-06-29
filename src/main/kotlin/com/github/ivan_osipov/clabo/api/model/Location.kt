package com.github.ivan_osipov.clabo.api.model

import com.google.gson.annotations.SerializedName

/**
 * @see <a href="https://core.telegram.org/bots/api#location">docs</a>
 */
class Location {

    @SerializedName("longitude")
    private var _longitude: Double? = null

    @SerializedName("latitude")
    private var _latitude: Double? = null

    val longitude: Double
        get() = _longitude!!

    val latitude: Double
        get() = _latitude!!

}