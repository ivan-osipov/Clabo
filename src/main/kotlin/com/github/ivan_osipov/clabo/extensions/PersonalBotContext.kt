package com.github.ivan_osipov.clabo.extensions

import com.github.ivan_osipov.clabo.Bot
import com.github.ivan_osipov.clabo.CommonBotContext
import com.github.ivan_osipov.clabo.internal.apiInteraction.SendParams
import com.github.ivan_osipov.clabo.model.KeyboardButton
import com.github.ivan_osipov.clabo.model.Message
import com.github.ivan_osipov.clabo.model.ReplyKeyboardMarkup
import com.github.ivan_osipov.clabo.utils.ChatId
import com.github.ivan_osipov.clabo.utils.Text

class PersonalBotContext(bot: Bot) : CommonBotContext(bot) {

    fun say(text: Text, chatId: ChatId) {
        val sendParams = SendParams(chatId, text)
        bot.api.sendMessage(sendParams)
    }

    fun onStartReply(text: Text, init: SendParams.() -> Unit) {
        onStart {
            it.update.message.reply(text, init)
        }
    }

    fun Message?.reply(text: Text, init: SendParams.() -> Unit) {
        val sendParams = SendParams(this!!.chat.id, text)
        sendParams.init()
        bot.api.sendMessage(sendParams)
    }

    fun SendParams.replyKeyboard(init: ReplyKeyboardMarkup.() -> Unit) {
        replyMarkup = ReplyKeyboardMarkup().apply {
            init()
        }
    }

    fun ReplyKeyboardMarkup.line(vararg buttons: KeyboardButton) {
        this.keyboard.add(buttons.toList())
    }

    fun ReplyKeyboardMarkup.oneButtonPerLine(vararg buttons: KeyboardButton) {
        for (button in buttons) {
            this.keyboard.add(listOf(button))
        }
    }

    fun ReplyKeyboardMarkup.button(text: Text) = KeyboardButton().apply {
        this.text = text
    }

    fun ReplyKeyboardMarkup.requestContactButton() = KeyboardButton().apply {
        this.requestContact = true
    }

    fun ReplyKeyboardMarkup.requestLocationButton() = KeyboardButton().apply {
        this.requestLocation = true
    }

    infix fun Message?.answer(text: Text) {
        if(this == null) {
            logger.warn("Trying answer but message is undefined")
        } else {
            val sendParams = SendParams(this.chat.id, text)
            bot.api.sendMessage(sendParams)
        }
    }

}