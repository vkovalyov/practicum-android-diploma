package ru.practicum.android.diploma.core.data.cache

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

private val Context.appPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

fun createAppPreferencesDataStore(context: Context): DataStore<Preferences> {
    return context.appPreferencesDataStore
}
