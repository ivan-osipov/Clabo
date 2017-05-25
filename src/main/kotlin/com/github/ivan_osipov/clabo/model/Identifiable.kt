package com.github.ivan_osipov.clabo.model

import kotlin.properties.Delegates

open class Identifiable {
    var id: Long by Delegates.notNull()
}