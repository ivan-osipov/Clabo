package com.github.ivan_osipov.clabo.api.output.dto

import com.github.ivan_osipov.clabo.utils.ChatId
import com.github.ivan_osipov.clabo.utils.FilePointer
import java.io.File

class SendAudioParams(chatId: ChatId,
                      filePointer: FilePointer? = null,
                      file: File? = null) : SendFileParams(chatId, filePointer, file) {

    override val queryId: String = Queries.SEND_AUDIO

    override val fileType: String = "audio"

    var duration: Int? = null

    var performer: String? = null

    override fun toListOfPairs(): MutableList<Pair<String, *>> {
        val listOfPairs = super.toListOfPairs()
        duration?.let { listOfPairs.add("duration" to it) }
        performer?.let { listOfPairs.add("performer" to it) }

        return listOfPairs
    }
}