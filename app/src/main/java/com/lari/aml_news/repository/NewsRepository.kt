package com.lari.aml_news.repository

import com.lari.aml_news.api.RetrofitInstance
import com.lari.aml_news.db.ArticleDatabase

class NewsRepository(
    val db: ArticleDatabase
) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)
}