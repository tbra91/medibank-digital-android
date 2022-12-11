package com.medibank.shop.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SourceDao {
    @Query("SELECT * FROM source")
    fun getAll(): Flow<List<SourceEntity>>

    @Query("SELECT EXISTS(SELECT * FROM source WHERE id = :id)")
    fun exists(id: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(source: SourceEntity)

    @Delete
    suspend fun delete(source: SourceEntity)
}
