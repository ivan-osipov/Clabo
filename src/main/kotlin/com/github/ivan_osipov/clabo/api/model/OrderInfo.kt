package com.github.ivan_osipov.clabo.api.model

import com.google.gson.annotations.SerializedName

/**
 * @see <a href="https://core.telegram.org/bots/api#orderinfo">docs</a>
 */
class OrderInfo {

    @SerializedName("name")
    var name: String? = null

    @SerializedName("phone_number")
    var phoneNumber: String? = null

    @SerializedName("email")
    var email: String? = null

    @SerializedName("shipping_address")
    var shippingAddress: ShippingAddress? = null

}