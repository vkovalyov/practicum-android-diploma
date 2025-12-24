package ru.practicum.android.diploma.features.search.domain.interactor

import ru.practicum.android.diploma.core.domain.model.ApiResult
import ru.practicum.android.diploma.features.search.domain.entity.SearchVacancies
import ru.practicum.android.diploma.features.search.domain.repository.SearchVacancyRepository

class SearchVacancyInteractorImpl(val repository: SearchVacancyRepository) : SearchVacancyInteractor {
    override suspend fun searchVacancies(query: Map<String, String>): ApiResult<SearchVacancies> {
        return repository.searchVacancies(query = query)
    }
}
