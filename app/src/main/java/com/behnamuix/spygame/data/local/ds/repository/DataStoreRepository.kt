package com.behnamuix.spygame.data.local.ds.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.behnamuix.spygame.data.local.ds.config.PrefKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreRepository(private val dataStore: DataStore<Preferences>) {
    fun getUserUseState(): Flow<Boolean> = dataStore.data.map {
        it[PrefKey.USER_USE_KEY] ?: false
    }

    fun getAgentCount(): Flow<Int> = dataStore.data.map {
        it[PrefKey.AGENT_COUNT_KEY] ?: 3
    }

    fun getSpyCount(): Flow<Int> = dataStore.data.map {
        it[PrefKey.SPY_COUNT_KEY] ?: 1
    }

    fun getLoggedInState(): Flow<Boolean> = dataStore.data.map {
        it[PrefKey.KEY_IS_LOGGED_IN] ?: false
    }

    suspend fun setLoggedInState(value: Boolean) {
        dataStore.edit {
            it[PrefKey.KEY_IS_LOGGED_IN] = value
        }
    }
    suspend fun setUserUseState(value: Boolean) {
        dataStore.edit {
            it[PrefKey.USER_USE_KEY] = value
        }
    }

    suspend fun setAgentCount(value: Int) {
        dataStore.edit {
            it[PrefKey.AGENT_COUNT_KEY] = value
        }
    }

    suspend fun setSpyCount(value: Int) {
        dataStore.edit {
            it[PrefKey.SPY_COUNT_KEY] = value
        }
    }
}