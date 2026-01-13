package ru.practicum.android.diploma.core.data.cache

import com.google.gson.Gson
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStorageClient<T : Any>(
    private val dataStore: DataStore<Preferences>,
    private val gson: Gson,
    private val dataKey: String,
    private val type: T
) : StorageClient<T> {

    private val jsonKey = stringPreferencesKey(dataKey)

    override val data: Flow<T?> = dataStore.data

        .map { preferences ->
            val json = preferences[jsonKey]
            if (json.isNullOrBlank()) {
                null
            } else {
                gson.fromJson(json, type::class.java)
            }
        }

    override suspend fun save(value: T) {
        val json = gson.toJson(value)
        dataStore.edit { prefs ->
            prefs[jsonKey] = json
        }
    }

    override suspend fun clear() {
        dataStore.edit { prefs ->
            prefs.remove(jsonKey)
        }
    }
}
