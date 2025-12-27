package ru.practicum.android.diploma.features.detail.domain.interactor

import ru.practicum.android.diploma.core.domain.model.ApiResult
import ru.practicum.android.diploma.features.detail.domain.entity.VacancyDetail
import ru.practicum.android.diploma.features.detail.domain.repository.VacancyDetailRepository

class VacancyDetailInteractorImpl(
    private val repository: VacancyDetailRepository
) : VacancyDetailInteractor {

    override suspend fun getVacancyById(id: String): ApiResult<VacancyDetail> {
        return repository.getVacancyById(id)
    }
}
