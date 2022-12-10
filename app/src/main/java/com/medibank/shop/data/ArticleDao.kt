package com.medibank.shop.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Query("SELECT * FROM article")
    fun getAll(): Flow<List<ArticleEntity>>

    @Query("SELECT EXISTS(SELECT * FROM article WHERE sourceId = :sourceId AND author = :author AND title = :title)")
    fun exists(sourceId: String, author: String, title: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: ArticleEntity)

    @Delete
    suspend fun delete(article: ArticleEntity)
}
