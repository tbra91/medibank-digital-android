package com.medibank.shop.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "source")
data class SourceEntity(
    @PrimaryKey val id: String,
    var name: String,
    var description: String,
    var url: String,
    var category: String,
    var language: String,
    var country: String,
) {
    @Ignore var isSelected = false
}
