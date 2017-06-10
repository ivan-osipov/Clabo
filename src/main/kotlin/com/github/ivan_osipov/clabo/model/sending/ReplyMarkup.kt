package com.github.ivan_osipov.clabo.model.sending

sealed class ReplyMarkup

class InlineKeyboardMarkup : ReplyMarkup()

class ReplyKeyboardMarkup : ReplyMarkup()

class ReplyKeyboardRemove : ReplyMarkup()

class ForceReply : ReplyMarkup()