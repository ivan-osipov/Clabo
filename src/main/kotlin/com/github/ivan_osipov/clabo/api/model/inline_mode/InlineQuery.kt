package com.github.ivan_osipov.clabo.api.model.inline_mode

import com.github.ivan_osipov.clabo.api.model.Identifiable
import com.github.ivan_osipov.clabo.api.model.Location
import com.github.ivan_osipov.clabo.api.model.User
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