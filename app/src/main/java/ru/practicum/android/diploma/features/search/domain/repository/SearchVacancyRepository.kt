package ru.practicum.android.diploma.features.search.domain.repository

import ru.practicum.android.diploma.core.domain.model.ApiResult
import ru.practicum.android.diploma.features.search.domain.entity.SearchVacancies

interface SearchVacancyRepository {
    suspend fun searchVacancies(query: Map<String, String>): ApiResult<SearchVacancies>
}
