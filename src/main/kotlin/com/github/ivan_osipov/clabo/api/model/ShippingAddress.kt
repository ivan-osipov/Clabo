package com.github.ivan_osipov.clabo.api.model

import com.google.gson.annotations.SerializedName

/**
 * @see <a href="https://core.telegram.org/bots/api#shippingaddress">docs</a>
 */
class ShippingAddress {

    @SerializedName("country_code")
    lateinit var countryCode: String

    @SerializedName("state")
    lateinit var state: String

    @SerializedName("city")
    lateinit var city: String

    @SerializedName("street_line1")
    lateinit var streetLine1: String

    @SerializedName("street_line2")
    lateinit var streetLine2: String

    @SerializedName("post_code")
    lateinit var postCode: String

}