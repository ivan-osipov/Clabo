package com.github.ivan_osipov.clabo.model

import com.github.ivan_osipov.clabo.exceptions.IncorrectApiUsage
import com.google.gson.annotations.SerializedName
import kotlin.properties.Delegates

/**
 * @see <a href="https://core.telegram.org/bots/api#message">docs</a>
 */
class Message : Identifiable() {
    @SerializedName("message_id")
    override lateinit var id: String

    @SerializedName("from")
    var from: User? = null

    var date: Long by Delegates.notNull()

    @SerializedName("chat")
    lateinit var chat: Chat

    @SerializedName("forward_from")
    var forwardFrom: User? = null

    @SerializedName("forward_from_chat")
    var forwardFromChat: Chat? = null

    @SerializedName("forward_from_message_id")
    var forwardFromMessageId: String? = null

    @SerializedName("forward_date")
    var forwardDate: Long? = null

    @SerializedName("reply_to_message")
    var replyToMessage: Message? = null

    @SerializedName("edit_date")
    var editDate: Long? = null

    @SerializedName("text")
    var text: String? = null

    @SerializedName("entities")
    var entities: List<MessageEntity>? = null

    @SerializedName("audio")
    var audio: Any? = null //todo Audio

    @SerializedName("document")
    var document: Any? = null //todo Document

    @SerializedName("game")
    var game: Any? = null //todo Game

    @SerializedName("photo")
    var photo: List<Any>? = null //todo PhotoSize

    @SerializedName("sticker")
    var sticker: Any? = null //todo Sticker

    @SerializedName("video")
    var video: Any? = null //todo Video

    @SerializedName("voice")
    var voice: Any? = null //todo Voice

    @SerializedName("video_note")
    var videoNote: Any? = null //todo VideoNote

    @SerializedName("new_chat_members")
    var newChatMembers: List<User>? = null

    @SerializedName("caption")
    var caption: String? = null
        get() {
            if(document == null && photo == null && video == null) {
                throw IncorrectApiUsage("Caption is supported only for document/photo/video")
            }
            return field
        }

    @SerializedName("contact")
    var contact: Contact? = null

    @SerializedName("location")
    var location: Location? = null

    @SerializedName("venue")
    var venue: Any? = null //todo Venue

    @SerializedName("new_chat_member")
    var newChatMember: User? = null

    @SerializedName("left_chat_member")
    var leftCharMember: User? = null

    @SerializedName("new_chat_title")
    var newChatTitle: String? = null

    @SerializedName("new_chat_photo")
    var newChatPhoto: List<Any>? = null //todo PhotoSize

    @SerializedName("delete_chat_photo")
    var deleteChatPhoto: Boolean = false

    @SerializedName("group_chat_created")
    var groupChatCreated: Boolean = false

    @SerializedName("supergroup_chat_created")
    var superGroupChatCreated: Boolean = false

    @SerializedName("channel_chat_created")
    var channelChatCreated: Boolean = false

    @SerializedName("migrate_to_chat_id")
    var migrateToChatId: String? = null

    @SerializedName("pinned_message")
    var pinnedMessage: Message? = null

    @SerializedName("invoice")
    var invoice: Any? = null //todo Invoice

    @SerializedName("successful_payment")
    var successfulPayment: Any? = null //todo SuccessfulPayment

}