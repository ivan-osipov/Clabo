package com.github.ivan_osipov.clabo.api.model

import com.google.gson.annotations.SerializedName

class ChatMember {

    @SerializedName("user")
    lateinit var user: User

    @SerializedName("status")
    lateinit var status: String

    @SerializedName("until_date")
    var untilDate: Int? = null

    @SerializedName("can_be_edited")
    var canBeEdited: Boolean = false

    @SerializedName("can_change_info")
    var canChangeInfo: Boolean = false

    @SerializedName("can_post_messages")
    var canPostMessages: Boolean = false

    @SerializedName("can_edit_messages")
    var canEditMessages: Boolean = false

    @SerializedName("can_delete_messages")
    var canDeleteMessages: Boolean = false

    @SerializedName("can_invite_users")
    var canInviteUsers: Boolean = false

    @SerializedName("can_restrict_members")
    var canRestrictMembers: Boolean = false

    @SerializedName("can_pin_messages")
    var canPinMessages: Boolean = false

    @SerializedName("can_promote_members")
    var canPromoteMembers: Boolean = false

    @SerializedName("can_send_messages")
    var canSendMessages: Boolean = true

    @SerializedName("can_send_media_messages")
    var canSendMediaMessages: Boolean = true

    @SerializedName("can_send_other_messages")
    var canSendOtherMessages: Boolean = true

    @SerializedName("can_add_web_page_previews")
    var canAddWebPagePreviews: Boolean = true

}