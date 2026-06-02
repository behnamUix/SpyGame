package com.behnamuix.spy.data.local.ds.config

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore(name = "game_intro_prefs")

//keys
object PrefKey {
    val USER_USE_KEY = booleanPreferencesKey("user_use")
    val AGENT_COUNT_KEY= intPreferencesKey("agent_count")
    val SPY_COUNT_KEY= intPreferencesKey("spy_count")
}