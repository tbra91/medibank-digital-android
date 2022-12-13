package com.medibank.shop.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.medibank.shop.data.ArticleRepository

class SavesViewModel(articleRepository: ArticleRepository) : ViewModel() {

    val articles = articleRepository.getAll().asLiveData()
}

class SavesViewModelFactory(private val articleRepository: ArticleRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SavesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return SavesViewModel(articleRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
