package com.medibank.shop.data

import androidx.annotation.WorkerThread

class ArticleRepository(private val articleDao: ArticleDao) {

    fun getAll() = articleDao.getAll()

    fun exists(article: ArticleEntity) = with(article) {
        articleDao.exists(sourceId, author, title)
    }

    @WorkerThread
    suspend fun insert(article: ArticleEntity) {
        articleDao.insert(article)
    }

    @WorkerThread
    suspend fun delete(article: ArticleEntity) {
        articleDao.delete(article)
    }
}
