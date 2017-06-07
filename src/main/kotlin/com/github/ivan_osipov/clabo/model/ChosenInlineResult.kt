package com.github.ivan_osipov.clabo.model

import com.google.gson.annotations.SerializedName

/**
 * @see <a href="https://core.telegram.org/bots/api#choseninlineresult">docs</a>
 */
class ChosenInlineResult {

    @SerializedName("result_id")
    lateinit var resultId: String

    @SerializedName("from")
    lateinit var from: User

    @SerializedName("query")
    lateinit var query: String

    @SerializedName("location")
    var location: Location? = null

    @SerializedName("inline_message_id")
    var inlineMessageId: String? = null

}