package com.github.ivan_osipov.clabo.api.model.inline_mode.contents

import com.github.ivan_osipov.clabo.api.model.ParseMode
import com.google.gson.annotations.SerializedName

class InputTextMessageContent : InputMessageContent() {

    @SerializedName("message_text")
    lateinit var messageText: String

    @SerializedName("parse_mode")
    private var _parseMode: String? = null

    var parseMode: ParseMode
        get() {
            val parseModeCopy = _parseMode
            return if(parseModeCopy == null) ParseMode.NONE else ParseMode.valueOf(parseModeCopy)
        }
        set(value) { _parseMode = value.content }

    @SerializedName("disable_web_page_preview")
    var disableWebPagePreview: Boolean = false

}