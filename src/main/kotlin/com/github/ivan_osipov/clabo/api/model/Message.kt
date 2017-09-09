package com.github.ivan_osipov.clabo.api.model

import com.github.ivan_osipov.clabo.api.exceptions.IncorrectApiUsage
import com.github.ivan_osipov.clabo.utils.ChatId
import com.github.ivan_osipov.clabo.utils.MessageId
import com.google.gson.annotations.SerializedName

/**
 * @see <a href="https://core.telegram.org/bots/api#message">docs</a>
 */
open class Message : Identifiable() {
    @SerializedName("message_id")
    override lateinit var id: MessageId

    @SerializedName("from")
    var from: User? = null

    @SerializedName("date")
    private val _date: Long? = null

    val date: Long
        get() = _date!!

    @SerializedName("chat")
    lateinit var chat: Chat

    @SerializedName("forward_from")
    var forwardFrom: User? = null

    @SerializedName("forward_from_chat")
    var forwardFromChat: Chat? = null

    @SerializedName("forward_from_message_id")
    var forwardFromMessageId: MessageId? = null

    @SerializedName("forward_signature")
    var forwardSignature: String? = null

    @SerializedName("forward_date")
    var forwardDate: Long? = null

    @SerializedName("reply_to_message")
    var replyToMessage: Message? = null

    @SerializedName("edit_date")
    var editDate: Long? = null

    @SerializedName("author_signature")
    var authorSignature: String? = null

    @SerializedName("text")
    var text: String? = null

    @SerializedName("entities")
    var entities: List<MessageEntity> = ArrayList()

    @SerializedName("audio")
    var audio: Audio? = null

    @SerializedName("document")
    var document: Document? = null

    @SerializedName("game")
    var game: Game? = null

    @SerializedName("photo")
    var photo: List<PhotoSize> = ArrayList()

    @SerializedName("sticker")
    var sticker: Sticker? = null

    @SerializedName("video")
    var video: Video? = null

    @SerializedName("voice")
    var voice: Voice? = null

    @SerializedName("video_note")
    var videoNote: VideoNote? = null

    @SerializedName("new_chat_members")
    var newChatMembers: List<User> = ArrayList()

    @SerializedName("caption")
    var caption: String? = null
        get() {
            if(document == null && photo.isEmpty() && video == null) {
                throw IncorrectApiUsage("Caption is supported only for document/photo/video")
            }
            return field
        }

    @SerializedName("contact")
    var contact: Contact? = null

    @SerializedName("location")
    var location: Location? = null

    @SerializedName("venue")
    var venue: Venue? = null

    @SerializedName("new_chat_member")
    var newChatMember: User? = null

    @SerializedName("left_chat_member")
    var leftCharMember: User? = null

    @SerializedName("new_chat_title")
    var newChatTitle: String? = null

    @SerializedName("new_chat_photo")
    var newChatPhoto: List<PhotoSize> = ArrayList()

    @SerializedName("delete_chat_photo")
    var deleteChatPhoto: Boolean = false

    @SerializedName("group_chat_created")
    var groupChatCreated: Boolean = false

    @SerializedName("supergroup_chat_created")
    var superGroupChatCreated: Boolean = false

    @SerializedName("channel_chat_created")
    var channelChatCreated: Boolean = false

    @SerializedName("migrate_to_chat_id")
    var migrateToChatId: ChatId? = null

    @SerializedName("migrate_from_chat_id")
    var migrateFromChatId: ChatId? = null

    @SerializedName("pinned_message")
    var pinnedMessage: Message? = null

    @SerializedName("invoice")
    var invoice: Invoice? = null

    @SerializedName("successful_payment")
    var successfulPayment: SuccessfulPayment? = null

}