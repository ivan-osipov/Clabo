package com.github.ivan_osipov.clabo.settings

class UpdatesParams(var offset: Long? = null,
                    var limit: Short? = null,
                    var timeout: Long? = null,
                    var allowedUpdates: List<String>? = null) {

    fun toListOfPairs() : List<Pair<String,*>> {
        return listOf("offset" to offset, "limit" to limit, "timeout" to timeout, "allowed_updates" to allowedUpdates)
    }
}