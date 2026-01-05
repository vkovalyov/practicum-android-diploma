package ru.practicum.android.diploma.features.detail.domain.repository

import ru.practicum.android.diploma.core.domain.model.ApiResult
import ru.practicum.android.diploma.features.detail.domain.entity.VacancyDetail

interface VacancyDetailRepository {
    suspend fun getVacancyById(id: String): ApiResult<VacancyDetail>
}
