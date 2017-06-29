package com.github.ivan_osipov.clabo.api.model.inline_mode

import com.github.ivan_osipov.clabo.api.model.Identifiable
import com.github.ivan_osipov.clabo.api.model.inline_mode.results.InlineQueryResult
import com.google.gson.annotations.SerializedName

class AnswerInlineQuery : Identifiable() {

    @SerializedName("inline_query_id")
    override lateinit var id: String

    @SerializedName("results")
    var results: List<InlineQueryResult> = ArrayList()

    @SerializedName("cache_time")
    var cacheTime: Long? = null

    @SerializedName("is_personal")
    var isPersonal: Boolean = false

    @SerializedName("next_offset")
    var nextOffset: String? = null

    @SerializedName("switch_pm_text")
    var switchPmText: String? = null

    @SerializedName("switch_pm_parameter")
    var switchPmParameter: String? = null

}