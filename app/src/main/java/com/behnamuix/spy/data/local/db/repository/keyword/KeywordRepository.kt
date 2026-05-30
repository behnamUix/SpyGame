package com.behnamuix.spy.data.local.db.repository.keyword

import com.behnamuix.spy.data.local.db.model.KeyWord

interface KeywordRepository {
    suspend fun getKeywords(): List<KeyWord>
    suspend fun addKeywords(word: KeyWord)
    suspend fun deleteKeywords(word: KeyWord)
}