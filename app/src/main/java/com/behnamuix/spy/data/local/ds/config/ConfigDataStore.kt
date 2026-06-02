package com.behnamuix.spy.data.local.ds.config

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore(name = "game_intro_prefs")

//keys
object PrefKey {
    val USER_USE_KEY = booleanPreferencesKey("user_use")
}