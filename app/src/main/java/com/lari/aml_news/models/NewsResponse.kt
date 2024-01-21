package com.lari.aml_news.models

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)