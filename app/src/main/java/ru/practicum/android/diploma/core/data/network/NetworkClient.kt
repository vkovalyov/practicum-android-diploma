package ru.practicum.android.diploma.core.data.network

import ru.practicum.android.diploma.core.data.dto.AreaDto
import ru.practicum.android.diploma.core.data.dto.IndustryDto
import ru.practicum.android.diploma.core.data.dto.VacancyDetail
import ru.practicum.android.diploma.core.data.dto.VacancyDto
import ru.practicum.android.diploma.core.domain.model.ApiResult

interface NetworkClient{
    suspend fun getVacancyById(id: String): ApiResult<VacancyDetail>

    suspend fun getIndustries(): ApiResult<List<IndustryDto>>

    suspend fun getAreas(): ApiResult<List<AreaDto>>

    suspend fun searchVacancies(query: Map<String, Any>): ApiResult<List<VacancyDto>>
}
