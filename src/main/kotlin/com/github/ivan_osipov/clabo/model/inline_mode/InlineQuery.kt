package com.github.ivan_osipov.clabo.model.inline_mode

import com.github.ivan_osipov.clabo.model.Identifiable
import com.github.ivan_osipov.clabo.model.Location
import com.github.ivan_osipov.clabo.model.User
import com.google.gson.annotations.SerializedName

/**
 * @see <a href="https://core.telegram.org/bots/api#inlinequery">docs</a>
 */
class InlineQuery : Identifiable() {

    @SerializedName("from")
    lateinit var from: User

    @SerializedName("query")
    lateinit var query: String

    @SerializedName("offset")
    lateinit var offset: String

    @SerializedName("location")
    var location: Location? = null

}