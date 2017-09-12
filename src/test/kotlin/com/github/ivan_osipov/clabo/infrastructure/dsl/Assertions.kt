package com.github.ivan_osipov.clabo.infrastructure.dsl

import com.github.ivan_osipov.clabo.dsl.BotResults
import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher
import org.hamcrest.MatcherAssert

infix fun BotResults.assertThatBotStopReason(matcher: Matcher<in Any?>) {
    MatcherAssert.assertThat(stopReason.toString(), stopReason, matcher)
}

val isNull: Matcher<Any?> = CoreMatchers.nullValue()
