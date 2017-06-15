package com.github.ivan_osipov.clabo.model.inline_mode.contents

import com.google.gson.annotations.SerializedName

class InputVenueMessageContent : InputLocationMessageContent() {

    @SerializedName("title")
    lateinit var title: String

    @SerializedName("address")
    lateinit var address: String

    @SerializedName("foursquare_id")
    var foursquareId: String? = null

}