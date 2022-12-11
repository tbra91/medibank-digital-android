package com.medibank.shop.data

import androidx.annotation.WorkerThread

class SourceRepository(private val sourceDao: SourceDao) {

    fun getAll() = sourceDao.getAll()

    @WorkerThread
    fun exists(source: SourceEntity) = sourceDao.exists(source.id)

    @WorkerThread
    suspend fun insert(source: SourceEntity) {
        sourceDao.insert(source)
    }

    @WorkerThread
    suspend fun delete(source: SourceEntity) {
        sourceDao.delete(source)
    }
}
