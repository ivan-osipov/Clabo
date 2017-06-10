package com.github.ivan_osipov.clabo.extensions

import com.github.ivan_osipov.clabo.Bot
import com.github.ivan_osipov.clabo.CommonBotContext
import com.github.ivan_osipov.clabo.internal.apiInteraction.SendParams
import com.github.ivan_osipov.clabo.model.Message
import com.github.ivan_osipov.clabo.utils.ChatId
import com.github.ivan_osipov.clabo.utils.Text

class PersonalBotContext(bot: Bot) : CommonBotContext(bot) {

    fun say(text: Text, chatId: ChatId) {
        val sendParams = SendParams(chatId, text)
        bot.api.sendMessage(sendParams)
    }

    infix fun Message.answer(text: Text) {
        val sendParams = SendParams(this.chat.id, text)
        bot.api.sendMessage(sendParams)
    }

}