package com.github.ivan_osipov.clabo.model

import com.google.gson.annotations.SerializedName

/**
 * @see <a href="https://core.telegram.org/bots/api#precheckoutquery">docs</a>
 */
class PreCheckoutQuery : Identifiable() {

    @SerializedName("from")
    lateinit var from: User

    @SerializedName("currency")
    lateinit var currency: String

    @SerializedName("total_amount")
    private val _totalAmount: Int? = null

    val totalAmount: Int = _totalAmount!!

    @SerializedName("invoice_payload")
    lateinit var invoicePayload: String

    @SerializedName("shipping_option_id")
    var shippingOptionId: String? = null

    @SerializedName("order_info")
    var orderInfo: OrderInfo? = null

}