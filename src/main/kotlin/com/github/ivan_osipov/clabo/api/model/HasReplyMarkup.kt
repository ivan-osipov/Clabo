package com.github.ivan_osipov.clabo.api.model

interface HasReplyMarkup<out T : ReplyMarkup> {
    val replyMarkup: T?
}