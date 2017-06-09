package com.github.ivan_osipov.clabo.model

import com.github.ivan_osipov.clabo.exceptions.IncorrectApiUsage
import com.github.ivan_osipov.clabo.utils.notOneOf
import com.google.gson.annotations.SerializedName

/**
 * @see <a href="https://core.telegram.org/bots/api#chat">docs</a>
 */
class Chat: Identifiable() {

    @SerializedName("type")
    private lateinit var _type: String

    @SerializedName("title")
    var title: String? = null
        get() {
            if(type.notOneOf(Type.SUPER_GROUP, Type.CHANNEL, Type.GROUP)) {
                throw IncorrectApiUsage("Type ${type.name} does not support 'title'")
            }
            return field
        }

    @SerializedName("username")
    var username: String? = null
        get() {
            if(type.notOneOf(Type.SUPER_GROUP, Type.PRIVATE, Type.CHANNEL)) {
                throw IncorrectApiUsage("Type ${type.name} does not support 'username'")
            }
            return field
        }

    @SerializedName("first_name")
    var firstName: String? = null
        get() {
            if(type != Type.PRIVATE) {
                throw IncorrectApiUsage("Type ${type.name} does not support 'first_name'")
            }
            return field
        }

    @SerializedName("last_name")
    var lastName: String? = null
        get() {
            if(type != Type.PRIVATE) {
                throw IncorrectApiUsage("Type ${type.name} does not support 'first_name'")
            }
            return field
        }

    @SerializedName("all_members_are_administrators")
    val allMembersAreAdministrators: Boolean = false

    val type: Type
        get() = Type.valueOf(_type)

    enum class Type(name: String) {
        PRIVATE("private"),
        GROUP("group"),
        SUPER_GROUP("supergroup"),
        CHANNEL("channel");
    }

}