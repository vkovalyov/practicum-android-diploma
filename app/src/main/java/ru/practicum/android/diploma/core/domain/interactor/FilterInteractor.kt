package ru.practicum.android.diploma.core.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.model.FilterPreferences

interface FilterInteractor {
    val filter: Flow<FilterPreferences?>
    suspend fun save(filter: FilterPreferences)
    suspend fun clear()
}
