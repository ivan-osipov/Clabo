package com.github.ivan_osipov.clabo.api.model

import com.google.gson.annotations.SerializedName

sealed class ReplyMarkup

class InlineKeyboardMarkup : ReplyMarkup() {
    @SerializedName("inline_keyboard")
    var keyboard: MutableList<List<InlineKeyboardButton>> = ArrayList()
}

sealed class SelectiveReplyMarkup : ReplyMarkup() {
    @SerializedName("selective")
    var selective: Boolean = false
}

class ReplyKeyboardMarkup : SelectiveReplyMarkup() {
    @SerializedName("keyboard")
    var keyboard: MutableList<List<KeyboardButton>> = ArrayList()

    @SerializedName("resize_keyboard")
    var resizeKeyboard: Boolean = false

    @SerializedName("one_time_keyboard")
    var oneTimeKeyboard: Boolean = false
}

class ReplyKeyboardRemove : SelectiveReplyMarkup() {
    @SerializedName("remove_keyboard")
    val removeKeyboard: Boolean = true
}

class ForceReply : SelectiveReplyMarkup() {
    @SerializedName("force_reply")
    val forceReply: Boolean = true
}