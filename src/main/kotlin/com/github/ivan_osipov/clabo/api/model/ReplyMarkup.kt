package com.github.ivan_osipov.clabo.api.model

import com.google.gson.annotations.SerializedName

sealed class ReplyMarkup(val holder: HasEditableReplyMarkup<*>)

class InlineKeyboardMarkup(holder: HasEditableReplyMarkup<*>) : ReplyMarkup(holder) {
    @SerializedName("inline_keyboard")
    var keyboard: MutableList<List<InlineKeyboardButton>> = ArrayList()
}

sealed class SelectiveReplyMarkup(holder: HasEditableReplyMarkup<*>) : ReplyMarkup(holder) {
    @SerializedName("selective")
    var selective: Boolean = false
}

class ReplyKeyboardMarkup(holder: HasEditableReplyMarkup<*>) : SelectiveReplyMarkup(holder) {
    @SerializedName("keyboard")
    var keyboard: MutableList<List<KeyboardButton>> = ArrayList()

    @SerializedName("resize_keyboard")
    var resizeKeyboard: Boolean = false

    @SerializedName("one_time_keyboard")
    var oneTimeKeyboard: Boolean = false
}

class ReplyKeyboardRemove(holder: HasEditableReplyMarkup<*>) : SelectiveReplyMarkup(holder) {
    @SerializedName("remove_keyboard")
    val removeKeyboard: Boolean = true
}

class ForceReply(holder: HasEditableReplyMarkup<*>) : SelectiveReplyMarkup(holder) {
    @SerializedName("force_reply")
    val forceReply: Boolean = true
}