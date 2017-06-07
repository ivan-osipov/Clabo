package com.github.ivan_osipov.clabo.model

import com.google.gson.annotations.SerializedName
import kotlin.properties.Delegates

/**
 * @see <a href="https://core.telegram.org/bots/api#precheckoutquery">docs</a>
 */
class PreCheckoutQuery : Identifiable() {

    @SerializedName("from")
    lateinit var from: User

    @SerializedName("currency")
    lateinit var currency: String

    var total_amount: Int by Delegates.notNull()

    val totalAmount: Int = total_amount

    @SerializedName("invoice_payload")
    lateinit var invoicePayload: String

    @SerializedName("shipping_option_id")
    var shippingOptionId: String? = null

    @SerializedName("order_info")
    var orderInfo: OrderInfo? = null

}