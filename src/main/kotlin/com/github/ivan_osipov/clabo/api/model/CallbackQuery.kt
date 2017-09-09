package com.github.ivan_osipov.clabo.api.model

import com.google.gson.annotations.SerializedName

/**
 * @see <a href="https://core.telegram.org/bots/api#callbackquery">docs</a>
 */
class CallbackQuery: Identifiable() {

    @SerializedName("from")
    lateinit var from: User

    @SerializedName("message")
    var message: Message? = null

    @SerializedName("inline_message_id")
    var inlineMessageId: String? = null

    @SerializedName("chat_instance")
    lateinit var chatInstance: String

    @SerializedName("data")
    var data: String? = null

    @SerializedName("game_short_name")
    var gameShortName: String? = null
}