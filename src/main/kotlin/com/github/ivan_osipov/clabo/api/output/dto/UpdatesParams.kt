package com.github.ivan_osipov.clabo.api.output.dto


data class UpdatesParams(var offset: Long? = null,
                    var limit: Short? = null,
                    var timeout: Long = 60000,
                    var allowedUpdates: List<String>? = null) {

    fun toListOfPairs() : List<Pair<String,*>> {
        return listOf("offset" to offset, "limit" to limit, "timeout" to timeout, "allowed_updates" to allowedUpdates)
    }
}