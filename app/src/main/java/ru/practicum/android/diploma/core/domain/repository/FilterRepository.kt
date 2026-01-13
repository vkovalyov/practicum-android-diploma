package ru.practicum.android.diploma.core.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.model.FilterPreferences

interface FilterRepository {
    val filter: Flow<FilterPreferences?>
    suspend fun save(filter: FilterPreferences)
    suspend fun clear()
}
