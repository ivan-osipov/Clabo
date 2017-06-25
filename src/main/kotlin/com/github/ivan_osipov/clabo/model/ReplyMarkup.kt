package com.github.ivan_osipov.clabo.model

import com.google.gson.annotations.SerializedName

sealed class ReplyMarkup

class InlineKeyboardMarkup : ReplyMarkup()

class ReplyKeyboardMarkup : ReplyMarkup() {
    @SerializedName("keyboard")
    var keyboard: MutableList<List<KeyboardButton>> = ArrayList()

    @SerializedName("resize_keyboard")
    var resizeKeyboard: Boolean = false

    @SerializedName("one_time_keyboard")
    var oneTimeKeyboard: Boolean = false

    @SerializedName("selective")
    var selective: Boolean = false
}

class ReplyKeyboardRemove : ReplyMarkup()

class ForceReply : ReplyMarkup()