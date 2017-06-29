package com.github.ivan_osipov.clabo.api.model

import com.google.gson.annotations.SerializedName

/**
 * @see <a href="https://core.telegram.org/bots/api#user">docs</a>
 */
class User : Identifiable() {
    @SerializedName("first_name")
    lateinit var firstName: String

    @SerializedName("last_name")
    var lastName: String? = null

    @SerializedName("username")
    var username: String? = null

    @SerializedName("language_code")
    var languageCode: String? = null
}