package ru.practicum.android.diploma.features.detail.presentation.mvvm

import ru.practicum.android.diploma.features.detail.domain.entity.VacancyDetail

sealed interface VacancyDetailState {

    data object Loading : VacancyDetailState

    data class Content(val vacancy: VacancyDetail) : VacancyDetailState

    data object Error : VacancyDetailState

    data object NoInternet : VacancyDetailState
}
