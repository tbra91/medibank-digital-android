package com.medibank.shop.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ArticleEntity::class, SourceEntity::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {

    abstract val articleDao: ArticleDao
    abstract val sourceDao: SourceDao

    companion object {

        @Volatile
        private var INSTANCE: NewsDatabase? = null

        @Synchronized
        fun getInstance(context: Context): NewsDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext, NewsDatabase::class.java, "news_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
