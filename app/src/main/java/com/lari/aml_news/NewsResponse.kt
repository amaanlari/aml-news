package com.lari.aml_news

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)