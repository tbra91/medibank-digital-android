package com.medibank.shop.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.medibank.shop.data.ArticleEntity
import com.medibank.shop.data.ArticleRepository
import com.medibank.shop.data.NewsDatabase
import kotlinx.coroutines.launch

class ArticleViewModel(application: Application) : AndroidViewModel(application) {

    var article: ArticleEntity? = null

    private val articleRepository =
        ArticleRepository(NewsDatabase.getInstance(application).articleDao)

    fun save() {
        viewModelScope.launch {
            article?.let { article -> articleRepository.insert(article) }
        }
    }

    fun delete() {
        viewModelScope.launch {
            article?.let { article -> articleRepository.delete(article) }
        }
    }
}
