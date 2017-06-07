package com.github.ivan_osipov.clabo.auth.extensions

import com.github.ivan_osipov.clabo.auth.AuthContext
import com.github.ivan_osipov.clabo.model.Contact

fun <T: AuthContext> T.askContact(callback: T.(contact: Contact) -> Unit) {
    val contact = Contact().apply {
        firstName = "John"
        lastName = "Doe"
        phoneNumber = "123654"
    }
    this.callback(contact)
}