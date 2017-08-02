package com.github.ivan_osipov.clabo.api.model

import com.github.ivan_osipov.clabo.utils.ChatId

interface HasReplyMarkup<out T : ReplyMarkup> {
    val chatId: ChatId?
    val replyMarkup: T?
}