package com.github.ivan_osipov.clabo.api.output.dto

import com.github.ivan_osipov.clabo.api.input.toJson
import com.github.ivan_osipov.clabo.api.model.HasEditableReplyMarkup
import com.github.ivan_osipov.clabo.api.model.ReplyMarkup
import com.github.ivan_osipov.clabo.utils.ChatId
import com.github.ivan_osipov.clabo.utils.FilePointer
import com.github.ivan_osipov.clabo.utils.MessageId
import java.io.File

abstract class SendFileParams(override val chatId: ChatId,
                              val filePointer: FilePointer? = null,
                              val file: File? = null) : OutputParams, HasEditableReplyMarkup<ReplyMarkup> {

    abstract val fileType: String

    var caption: String? = null

    var disableNotification: Boolean = false

    var replyToMessageId: MessageId? = null

    override var replyMarkup: ReplyMarkup? = null

    override fun toListOfPairs(): MutableList<Pair<String, *>> {
        val paramsList = mutableListOf(
                "chat_id" to chatId,
                "disable_notification" to disableNotification,
                "reply_to_message_id" to replyToMessageId
        )
        filePointer?.let { paramsList.add(fileType to it) }
        replyMarkup?.let { paramsList.add("reply_markup" to it.toJson()) }
        caption?.let { paramsList.add("caption" to caption) }

        return paramsList
    }
}