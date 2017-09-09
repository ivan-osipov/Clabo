package com.github.ivan_osipov.clabo.api.output.dto

import com.github.ivan_osipov.clabo.api.input.toJson
import com.github.ivan_osipov.clabo.api.model.ReplyMarkup
import com.github.ivan_osipov.clabo.utils.ChatId
import com.github.ivan_osipov.clabo.utils.FilePointer
import com.github.ivan_osipov.clabo.utils.MessageId
import java.io.File

class SendPhotoParams(val chatId: ChatId,
                      val photo: FilePointer? = null,
                      val photoFile: File? = null) : OutputParams {

    override val queryId: String = Queries.SEND_PHOTO

    var caption: String? = null

    var disableNotification: Boolean = false

    var replyToMessageId: MessageId? = null

    var replyMarkup: ReplyMarkup? = null

    override fun toListOfPairs(): List<Pair<String, *>> {
        val paramsList = mutableListOf(
                "chat_id" to chatId,
                "disable_notification" to disableNotification,
                "reply_to_message_id" to replyToMessageId
        )
        photo?.let { paramsList.add("photo" to it) }
        replyMarkup?.let { paramsList.add("reply_markup" to it.toJson()) }
        caption?.let { paramsList.add("caption" to caption) }

        return paramsList
    }

}