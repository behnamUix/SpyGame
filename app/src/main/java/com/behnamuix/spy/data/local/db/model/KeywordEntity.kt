package com.behnamuix.spy.data.local.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class KeywordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    val word: String,
)

data class KeyWord(
    val id: Int=0,
    val word: String,
)