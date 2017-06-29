package com.github.ivan_osipov.clabo.api.model.inline_mode.results

import com.github.ivan_osipov.clabo.api.model.Identifiable
import com.github.ivan_osipov.clabo.api.model.InlineKeyboardMarkup
import com.google.gson.annotations.SerializedName

open class InlineQueryResult : Identifiable() {

    @SerializedName("type")
    var _type: String? = null

    val type: String
        get() = _type!!

    @SerializedName("reply_markup")
    var replyMarkup: InlineKeyboardMarkup? = null

    enum class Type(name: String) {
        ARTICLE("article"),
        PHOTO("photo"),
        GIF("gif"),
        MPEG4_GIF("mpeg4_gif"),
        VIDEO("video"),
        AUDIO("audio"),
        VOICE("voice"),
        DOCUMENT("document"),
        LOCATION("location"),
        VENUE("venue"),
        CONTACT("contact"),
        GAME("game"),
        STICKER("sticker")
    }

}