package ru.practicum.android.diploma.features.search.presentation.mvvm

import ru.practicum.android.diploma.features.search.domain.entity.SearchVacancies

sealed interface SearchVacancyState {

    data object Initial : SearchVacancyState

    data object Loading : SearchVacancyState

    data class LoadingPage(val searchVacancies: SearchVacancies) : SearchVacancyState

    data class Content(val searchVacancies: SearchVacancies) : SearchVacancyState

    data object ContentEmpty : SearchVacancyState

    data object Error : SearchVacancyState

    data object NoInternet : SearchVacancyState
}
