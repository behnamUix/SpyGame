package com.behnamuix.spy.data.local.repository

import com.behnamuix.spy.data.local.db.model.KeyWord

interface KeywordRepository {
    suspend fun getKeywords(): List<KeyWord>
    suspend fun addKeywords(word: String)
    suspend fun deleteKeywords(word: KeyWord)
}