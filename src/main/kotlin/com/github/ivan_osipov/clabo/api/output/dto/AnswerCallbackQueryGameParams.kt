package com.github.ivan_osipov.clabo.api.output.dto

import com.github.ivan_osipov.clabo.utils.Text

/**
 * @see AnswerCallbackQueryParams
 */
class AnswerCallbackQueryGameParams(callbackQueryId: String,
                                    text: Text? = null,
                                    showAlert: Boolean = false,
                                    cacheTime: Int? = null,
                                    var url: String? = null)
    : AnswerCallbackQueryParams(callbackQueryId, text, showAlert, cacheTime) {
    override fun toListOfPairs(): MutableList<Pair<String, *>> {
        return super.toListOfPairs().apply {
            add("url" to url)
        }
    }
}