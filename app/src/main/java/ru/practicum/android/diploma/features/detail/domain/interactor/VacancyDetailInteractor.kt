package ru.practicum.android.diploma.features.detail.domain.interactor

import ru.practicum.android.diploma.core.domain.model.ApiResult
import ru.practicum.android.diploma.features.detail.domain.entity.VacancyDetail

interface VacancyDetailInteractor {
    suspend fun getVacancyById(id: String): ApiResult<VacancyDetail>
}
