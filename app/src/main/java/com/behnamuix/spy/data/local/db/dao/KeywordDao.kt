package com.behnamuix.spy.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.behnamuix.spy.data.local.db.model.KeywordEntity


@Dao
interface KeywordDao {
    @Query("SELECT * FROM words ORDER BY word ASC ")
    suspend fun getAll(): MutableList<KeywordEntity>

    @Insert
    suspend fun insert(words: String)

    @Delete
    suspend fun delete(word: KeywordEntity)
}