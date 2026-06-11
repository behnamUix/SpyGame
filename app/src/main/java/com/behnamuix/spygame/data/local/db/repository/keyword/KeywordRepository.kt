package com.behnamuix.spygame.data.local.db.repository.keyword

import com.behnamuix.spygame.data.local.db.model.KeyWord

interface KeywordRepository {
    suspend fun getKeywords(): List<KeyWord>
    suspend fun addKeywords(word: KeyWord)
    suspend fun deleteKeywords(word: KeyWord)
}