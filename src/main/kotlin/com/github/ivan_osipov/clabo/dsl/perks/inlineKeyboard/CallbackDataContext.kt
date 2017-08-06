package com.github.ivan_osipov.clabo.dsl.perks.inlineKeyboard

import com.github.ivan_osipov.clabo.api.model.CallbackQuery
import com.github.ivan_osipov.clabo.api.model.Update
import com.github.ivan_osipov.clabo.utils.set
import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.Multimap

class CallbackDataContext {

    val register: Multimap<String, (CallbackQuery, Update) -> Unit> = ArrayListMultimap.create()

    fun clean() {
        register.clear()
    }

    fun register(data: String, queryCallback : (CallbackQuery) -> Unit) {
        register[data] = { query, _ -> queryCallback(query) }
    }

    fun register(data: String, queryCallback : (CallbackQuery, Update) -> Unit) {
        register[data] = queryCallback
    }

}