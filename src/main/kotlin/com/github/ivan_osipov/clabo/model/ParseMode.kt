package com.github.ivan_osipov.clabo.model

enum class ParseMode(val content: String?) {
    MARKDOWN("Markdown"),
    HTML("HTML"),
    NONE(null);
}