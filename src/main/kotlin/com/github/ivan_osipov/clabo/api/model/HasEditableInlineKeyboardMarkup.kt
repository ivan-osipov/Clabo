package com.github.ivan_osipov.clabo.api.model

interface HasEditableInlineKeyboardMarkup : HasInlineKeyboardMarkup, HasEditableReplyMarkup<InlineKeyboardMarkup> {
    override var replyMarkup: InlineKeyboardMarkup?
}