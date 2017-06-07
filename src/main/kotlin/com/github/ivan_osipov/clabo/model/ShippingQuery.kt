package com.github.ivan_osipov.clabo.model

import com.google.gson.annotations.SerializedName

/**
 * @see <a href="https://core.telegram.org/bots/api#shippingquery">docs</a>
 */
class ShippingQuery : Identifiable() {

    @SerializedName("from")
    lateinit var from: User

    @SerializedName("invoice_payload")
    lateinit var invoicePayload: String

    @SerializedName("shipping_address")
    lateinit var schippingAddress: ShippingAddress
}