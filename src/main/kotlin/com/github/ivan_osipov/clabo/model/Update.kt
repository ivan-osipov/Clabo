package com.github.ivan_osipov.clabo.model

import com.google.gson.annotations.SerializedName

/**
 * @see <a href="https://core.telegram.org/bots/api#update">docs</a>
 */
class Update: Identifiable() {

    @SerializedName("update_id")
    override lateinit var id: String

    @SerializedName("message")
    var message: Message? = null

    @SerializedName("edited_message")
    var editedMessage: Message? = null

    @SerializedName("channel_post")
    var channelPost: Message? = null

    @SerializedName("edited_channel_post")
    var editedChannelPost: Message? = null

    @SerializedName("inline_query")
    var inlineQuery: InlineQuery? = null

    @SerializedName("chosen_inline_result")
    var chosenInlineResult: ChosenInlineResult? = null

    @SerializedName("callback_query")
    var callbackQuery: CallbackQuery? = null

    @SerializedName("shipping_query")
    var shippingQuery: ShippingQuery? = null

    @SerializedName("pre_checkout_query")
    var preCheckoutQuery: PreCheckoutQuery? = null

}