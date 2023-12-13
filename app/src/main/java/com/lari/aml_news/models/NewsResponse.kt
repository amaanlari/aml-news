package com.lari.aml_news.models

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)