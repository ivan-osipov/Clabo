package com.github.ivan_osipov.clabo.api.model

import com.google.gson.annotations.SerializedName

class InlineKeyboardButton(
        @SerializedName("text")
        var text: String
) {

    @SerializedName("url")
    var url: String? = null

    @SerializedName("callback_data")
    var callbackData: String? = null

    @SerializedName("switch_inline_query")
    var switchInlineQuery: String? = null

    @SerializedName("switch_inline_query_current_chat")
    var switchInlineQueryCurrentChat: String? = null

    @SerializedName("callback_game")
    var callbackGame: Any? = null //todo CallbackGame

    @SerializedName("pay")
    var pay: Boolean = false

}