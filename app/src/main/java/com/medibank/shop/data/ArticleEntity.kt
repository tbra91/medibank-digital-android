package com.medibank.shop.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article")
data class ArticleEntity(
    var sourceId: String?,
    var author: String?,
    var title: String?,
    var description: String?,
    @PrimaryKey var url: String,
    var urlToImage: String?,
    var publishedAt: String?,
    var content: String?
)
