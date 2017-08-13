package com.github.ivan_osipov.clabo.api.output.dto


data class UpdatesParams(var offset: Long? = null,
                    var limit: Short? = null,
                    var timeout: Long? = null,
                    var allowedUpdates: List<String>? = null) : OutputParams {

    override val queryId: String
        get() = Queries.GET_UPDATES

    override fun toListOfPairs() : MutableList<Pair<String,*>> {
        return mutableListOf(
                "offset" to offset,
                "limit" to limit,
                "timeout" to timeout,
                "allowed_updates" to allowedUpdates
        )
    }
}