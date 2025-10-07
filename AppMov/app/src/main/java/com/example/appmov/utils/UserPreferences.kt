package com.example.appmov.utils

import android.content.Context
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "user_prefs")

object UserPreferences {
    private val FONT_SIZE_KEY = floatPreferencesKey("font_size")

    fun getFontSize(context: Context): Flow<Float> =
        context.dataStore.data.map { prefs -> prefs[FONT_SIZE_KEY] ?: 16f } // por defecto 16sp

    suspend fun saveFontSize(context: Context, size: Float) {
        context.dataStore.edit { prefs -> prefs[FONT_SIZE_KEY] = size }
    }
}
