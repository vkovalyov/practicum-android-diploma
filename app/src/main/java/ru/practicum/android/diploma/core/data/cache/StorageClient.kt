package ru.practicum.android.diploma.core.data.cache

import kotlinx.coroutines.flow.Flow

interface StorageClient<T : Any> {
    val data: Flow<T?>
    suspend fun save(value: T)
    suspend fun clear()
}
