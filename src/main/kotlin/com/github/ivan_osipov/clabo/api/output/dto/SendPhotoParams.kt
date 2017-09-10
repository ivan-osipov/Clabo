package com.github.ivan_osipov.clabo.api.output.dto

import com.github.ivan_osipov.clabo.utils.ChatId
import com.github.ivan_osipov.clabo.utils.FilePointer
import java.io.File

class SendPhotoParams(chatId: ChatId,
                      photo: FilePointer? = null,
                      photoFile: File? = null) : SendFileParams(chatId, photo, photoFile) {

    override val queryId: String = Queries.SEND_PHOTO

    override val fileType: String = "photo"

}