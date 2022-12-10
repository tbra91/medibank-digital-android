package com.medibank.shop.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.medibank.shop.data.ArticleEntity
import com.medibank.shop.data.ArticleRepository
import com.medibank.shop.data.NewsDatabase
import kotlinx.coroutines.launch

class ArticleViewModel(application: Application) : AndroidViewModel(application) {

    var article: ArticleEntity? = null

    private val articleRepository =
        ArticleRepository(NewsDatabase.getInstance(application).articleDao)

    fun isSaved() = article?.let { article -> articleRepository.exists(article).asLiveData() }

    fun save() {
        article?.let { article ->
            viewModelScope.launch { articleRepository.insert(article) }
        }
    }

    fun delete() {
        article?.let { article ->
            viewModelScope.launch { articleRepository.delete(article) }
        }
    }
}
