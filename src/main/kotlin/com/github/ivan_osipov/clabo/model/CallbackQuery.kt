package com.github.ivan_osipov.clabo.model

import com.google.gson.annotations.SerializedName

/**
 * @see <a href="https://core.telegram.org/bots/api#callbackquery">docs</a>
 */
class CallbackQuery {
    @SerializedName("id")
    lateinit var id: String

    @SerializedName("from")
    lateinit var from: User

    @SerializedName("chat_instance")
    lateinit var chatInstance: String

    @SerializedName("message")
    var message: Message? = null

    @SerializedName("inline_message_id")
    var inlineMessageId: String? = null

    @SerializedName("data")
    var data: String? = null

    @SerializedName("game_short_name")
    var gameShortName: String? = null
}