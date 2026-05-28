package com.behnamuix.spy.data.local.repository

import com.behnamuix.spy.data.local.db.dao.KeywordDao
import com.behnamuix.spy.data.local.db.model.KeyWord
import com.behnamuix.spy.data.local.db.model.toKeyword
import com.behnamuix.spy.data.local.db.model.toKeywordEntity

class KeywordRepositoryImpl(private val dao: KeywordDao) : KeywordRepository {
    override suspend fun getKeywords(): List<KeyWord> {
        return dao.getAll().map {
            it.toKeyword()
        }
    }

    override suspend fun addKeywords(word: KeyWord) {
        dao.insert(word.toKeywordEntity())
    }

    override suspend fun deleteKeywords(word: KeyWord) {
        dao.delete(word.toKeywordEntity())
    }
}