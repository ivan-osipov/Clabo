package com.github.ivan_osipov.clabo.api.model

interface HasEditableReplyMarkup<T : ReplyMarkup> : HasReplyMarkup<T> {
    override var replyMarkup: T?
}