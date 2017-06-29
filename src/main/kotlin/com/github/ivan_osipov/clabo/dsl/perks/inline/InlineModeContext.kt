package com.github.ivan_osipov.clabo.dsl.perks.inline

import com.github.ivan_osipov.clabo.api.model.Update
import com.github.ivan_osipov.clabo.api.model.inline_mode.AnswerInlineQuery
import com.github.ivan_osipov.clabo.api.model.inline_mode.InlineQuery

class InlineModeContext {
    val inlineQueryCallbacks: MutableList<(InlineQuery, Update) -> Unit> = ArrayList()
    val inlineQueryAnswers: MutableList<(InlineQuery) -> AnswerInlineQuery?> = ArrayList()

    fun onInlineQuery(inlineQueryCallback: (InlineQuery, Update) -> Unit) {
        inlineQueryCallbacks.add(inlineQueryCallback)
    }

    fun onInlineQuery(inlineQueryCallback: (InlineQuery) -> Unit) {
        inlineQueryCallbacks.add { inlineQuery, _ ->
            inlineQueryCallback(inlineQuery)
        }
    }

    fun register(recognizer: InlineQueryRecognizer, answerConfig: AnswerInlineQuery.() -> Unit = {}) {

        inlineQueryAnswers.add { query ->
            val queryResults = recognizer.recognize(query)
            if(queryResults.successfulRecognition) {
                val answer = AnswerInlineQuery().apply {
                    id = query.id
                    results = queryResults.results
                    cacheTime = queryResults.cacheTime
                    isPersonal = queryResults.isPersonalResults
                    nextOffset = queryResults.nextOffset
                    answerConfig.invoke(this)
                }
                return@add answer
            }
            return@add null
        }
    }
}