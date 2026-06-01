package com.behnamuix.spy.data.local.db.config

import androidx.room.Database
import androidx.room.RoomDatabase
import com.behnamuix.spy.data.local.db.dao.KeywordDao
import com.behnamuix.spy.data.local.db.model.KeywordEntity

@Database(entities = [KeywordEntity::class], version = 2, exportSchema = false)
abstract class SpyDatabase : RoomDatabase() {
    abstract fun keyWordDao(): KeywordDao

}