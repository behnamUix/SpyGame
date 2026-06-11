package com.behnamuix.spygame.data.local.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.behnamuix.spygame.data.local.db.model.KeywordEntity


@Dao
interface KeywordDao {
    @Query("SELECT * FROM words ORDER BY word ASC")
    suspend fun getAll(): MutableList<KeywordEntity>

    // 🔴 ورودی تابع از String به KeywordEntity اصلاح شد
    @Insert
    suspend fun insert(wordEntity: KeywordEntity)

    @Delete
    suspend fun delete(word: KeywordEntity)
}