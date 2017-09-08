package com.github.ivan_osipov.clabo.api.internal

import com.github.ivan_osipov.clabo.api.model.Chat
import com.github.ivan_osipov.clabo.api.model.Update
import com.github.ivan_osipov.clabo.api.model.User
import com.github.ivan_osipov.clabo.api.output.dto.GetChatParams
import com.github.ivan_osipov.clabo.api.output.dto.UpdatesParams

interface IncomingInteractionApi {

    val defaultUpdatesParams: UpdatesParams

    fun getMe(): User

    fun getMe(callback: (User) -> Unit)

    fun getChat(params: GetChatParams) : Chat

    fun getChat(params: GetChatParams, callback: (Chat) -> Unit)

    fun getUpdates(params: UpdatesParams = defaultUpdatesParams): List<Update>

    fun getUpdates(params: UpdatesParams, callback: (List<Update>) -> Unit)
}