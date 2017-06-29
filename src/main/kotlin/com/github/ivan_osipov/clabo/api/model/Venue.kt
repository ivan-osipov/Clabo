package com.github.ivan_osipov.clabo.api.model

import com.google.gson.annotations.SerializedName

class Venue {

    @SerializedName("location")
    lateinit var location: Location

    @SerializedName("title")
    lateinit var title: String

    @SerializedName("address")
    lateinit var address: String

    @SerializedName("foursquare_id")
    var foursquareId: String? = null

}