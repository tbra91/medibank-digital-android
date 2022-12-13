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

    /** The currently selected [ArticleEntity]. */
    var article: ArticleEntity? = null

    private val articleRepository =
        ArticleRepository(NewsDatabase.getInstance(application).articleDao)

    /**
     * Returns the current saved state of the [article] determined by its existence in the
     * [ArticleRepository].
     *
     * @return the current saved state of the [article]
     */
    fun isSaved() = article?.let { article -> articleRepository.exists(article).asLiveData() }

    /** Saves the [article], insert it into the [ArticleRepository]. */
    fun save() {
        article?.let { article ->
            viewModelScope.launch { articleRepository.insert(article) }
        }
    }

    /** Deletes the [article], removing it from the [ArticleRepository]. */
    fun delete() {
        article?.let { article ->
            viewModelScope.launch { articleRepository.delete(article) }
        }
    }
}
