package com.lari.aml_news.ui

import androidx.lifecycle.ViewModel
import com.lari.aml_news.repository.NewsRepository

class NewsViewModel(
    val newsRepository: NewsRepository
) : ViewModel(){
}