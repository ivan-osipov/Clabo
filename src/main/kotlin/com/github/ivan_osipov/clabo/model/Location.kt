package com.github.ivan_osipov.clabo.model

import kotlin.properties.Delegates

/**
 * @see <a href="https://core.telegram.org/bots/api#location">docs</a>
 */
class Location {

    var longitude: Double by Delegates.notNull()

    var latitude: Double by Delegates.notNull()

}