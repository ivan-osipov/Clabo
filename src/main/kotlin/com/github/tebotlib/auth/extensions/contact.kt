package com.github.tebotlib.auth.extensions

import com.github.tebotlib.auth.AuthContext
import com.github.tebotlib.model.Contact

fun <T: AuthContext> T.askContact(callback: T.(contact: Contact) -> Unit) {
    val contact = Contact("a", "b" ,"c") //todo
    this.callback(contact)
}