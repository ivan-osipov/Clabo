package com.github.ivan_osipov.clabo.api.model

interface HasInlineKeyboardMarkup : HasReplyMarkup<InlineKeyboardMarkup> {
    override val replyMarkup: InlineKeyboardMarkup?
}