package com.github.ivan_osipov.clabo.api.output.dto

import com.github.ivan_osipov.clabo.utils.ChatId
import com.github.ivan_osipov.clabo.utils.FilePointer
import java.io.File

class SendPhotoParams(chatId: ChatId,
                      filePointer: FilePointer? = null,
                      file: File? = null) : SendFileParams(chatId, filePointer, file) {

    override val queryId: String = Queries.SEND_PHOTO

    override val fileType: String = "photo"

}