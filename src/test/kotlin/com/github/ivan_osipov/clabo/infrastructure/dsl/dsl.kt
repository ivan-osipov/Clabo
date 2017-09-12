package com.github.ivan_osipov.clabo.infrastructure.dsl

import com.github.ivan_osipov.clabo.api.model.Chat
import com.github.ivan_osipov.clabo.api.model.Message
import com.github.ivan_osipov.clabo.api.model.Update
import com.github.ivan_osipov.clabo.api.model.User
import java.util.concurrent.ThreadLocalRandom

fun update(init: Update.() -> Unit) : Update {
    val update = Update()
    update.id = ThreadLocalRandom.current().nextLong(1_000_000).toString()
    update.init()

    return update
}

fun Update.message(init: Message.() -> Unit) : Message {
    val message = Message()
    message.id = ThreadLocalRandom.current().nextLong(1_000_000).toString()
    message.from()
    message.init()
    this.message = message

    return message
}

fun Message.from(firstName: String = "defaultUser") : User {
    val user = User()
    user.id = ThreadLocalRandom.current().nextLong(1_000_000).toString()
    user.firstName = firstName
    this.from = user

    val chat = Chat()
    chat.id = user.id
    this.chat = chat

    return user
}
fun user(init: User.() -> Unit): User {
    val user = User()
    user.id = ThreadLocalRandom.current().nextLong(1_000_000).toString()
    user.init()
    return user
}