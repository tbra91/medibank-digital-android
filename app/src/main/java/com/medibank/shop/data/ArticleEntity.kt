package com.medibank.shop.data

import androidx.room.Entity

@Entity(tableName = "article", primaryKeys = ["author", "title"])
data class ArticleEntity(
    var sourceId: String,
    var author: String,
    var title: String,
    var description: String,
    var url: String,
    var urlToImage: String,
    var publishedAt: String,
    var content: String
)
