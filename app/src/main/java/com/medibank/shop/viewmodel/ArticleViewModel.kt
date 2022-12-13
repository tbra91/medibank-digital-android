package com.medibank.shop.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.medibank.shop.data.ArticleEntity
import com.medibank.shop.data.ArticleRepository
import kotlinx.coroutines.launch

class ArticleViewModel(private val articleRepository: ArticleRepository) : ViewModel() {

    /** The currently selected [ArticleEntity]. */
    var article: ArticleEntity? = null

    /**
     * Returns the current saved state of the [article] determined by its existence in the
     * [articleRepository].
     *
     * @return the current saved state of the [article]
     */
    fun isSaved() = article?.let { article -> articleRepository.exists(article).asLiveData() }

    /** Saves the [article], insert it into the [articleRepository]. */
    fun save() {
        article?.let { article ->
            viewModelScope.launch { articleRepository.insert(article) }
        }
    }

    /** Deletes the [article], removing it from the [articleRepository]. */
    fun delete() {
        article?.let { article ->
            viewModelScope.launch { articleRepository.delete(article) }
        }
    }
}

class ArticleViewModelFactory(private val articleRepository: ArticleRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArticleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return ArticleViewModel(articleRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
