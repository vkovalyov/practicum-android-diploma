package ru.practicum.android.diploma.core.data.network

import ru.practicum.android.diploma.core.data.dto.Area
import ru.practicum.android.diploma.core.data.dto.Industry
import ru.practicum.android.diploma.core.data.dto.SearchVacanciesResponse
import ru.practicum.android.diploma.core.data.dto.VacancyDetail
import ru.practicum.android.diploma.core.domain.model.ApiResult

interface NetworkClient {
    suspend fun getVacancyById(id: String): ApiResult<VacancyDetail>

    suspend fun getIndustries(): ApiResult<List<Industry>>

    suspend fun getAreas(): ApiResult<List<Area>>

    suspend fun searchVacancies(query: Map<String, String>): ApiResult<SearchVacanciesResponse>
}
