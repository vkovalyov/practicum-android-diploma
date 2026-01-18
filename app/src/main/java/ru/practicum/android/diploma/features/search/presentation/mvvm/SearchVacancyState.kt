package ru.practicum.android.diploma.features.search.presentation.mvvm

import ru.practicum.android.diploma.features.search.domain.entity.SearchVacancies

sealed interface SearchVacancyState {
    val searchText: String

    data class Initial(override val searchText: String = "") : SearchVacancyState

    data class Loading(override val searchText: String = "") : SearchVacancyState

    data class LoadingPage(val searchVacancies: SearchVacancies, override val searchText: String = "") :
        SearchVacancyState

    data class Content(val searchVacancies: SearchVacancies, override val searchText: String = "") : SearchVacancyState

    data class ContentEmpty(override val searchText: String = "") : SearchVacancyState

    data class Error(override val searchText: String = "") : SearchVacancyState

    data class NoInternet(override val searchText: String = "") : SearchVacancyState
}
