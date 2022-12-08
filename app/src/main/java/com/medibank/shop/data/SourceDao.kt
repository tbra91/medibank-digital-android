package com.medibank.shop.data

import androidx.room.*

@Dao
interface SourceDao {
    @Query("SELECT * FROM source WHERE id = :id")
    suspend fun get(id: String): SourceEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(source: SourceEntity)

    @Delete
    suspend fun delete(source: SourceEntity)

    @Update
    suspend fun update(source: SourceEntity)
}
