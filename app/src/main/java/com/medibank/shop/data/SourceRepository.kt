package com.medibank.shop.data

import androidx.annotation.WorkerThread

class SourceRepository(private val sourceDao: SourceDao) {

    fun getAll() = sourceDao.getAll()

    @WorkerThread
    suspend fun get(id: String) = sourceDao.get(id)

    @WorkerThread
    suspend fun insert(source: SourceEntity) {
        sourceDao.insert(source)
    }

    @WorkerThread
    suspend fun delete(source: SourceEntity) {
        sourceDao.delete(source)
    }

    @WorkerThread
    suspend fun update(source: SourceEntity) {
        sourceDao.update(source)
    }
}
