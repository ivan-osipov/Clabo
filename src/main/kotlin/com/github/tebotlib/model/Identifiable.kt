package com.github.tebotlib.model

import kotlin.properties.Delegates

open class Identifiable {
    var id: Long by Delegates.notNull()
}