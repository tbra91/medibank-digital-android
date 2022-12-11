package com.medibank.shop.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Query("SELECT * FROM article")
    fun getAll(): Flow<List<ArticleEntity>>

    @Query("SELECT EXISTS(SELECT * FROM article WHERE url = :url)")
    fun exists(url: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: ArticleEntity)

    @Delete
    suspend fun delete(article: ArticleEntity)
}
