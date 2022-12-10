package com.medibank.shop.data

import androidx.annotation.WorkerThread

class ArticleRepository(private val articleDao: ArticleDao) {

    fun getAll() = articleDao.getAll()

    @WorkerThread
    suspend fun insert(article: ArticleEntity) {
        articleDao.insert(article)
    }

    @WorkerThread
    suspend fun delete(article: ArticleEntity) {
        articleDao.delete(article)
    }
}
