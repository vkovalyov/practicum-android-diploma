package ru.practicum.android.diploma.features.favorite.presentation.mvvm

import ru.practicum.android.diploma.features.favorite.domain.entity.FavoriteVacancy

sealed interface FavoriteState {
    data object Loading : FavoriteState
    data object Empty : FavoriteState
    data object Error : FavoriteState
    data class Content(val vacancies: List<FavoriteVacancy>) : FavoriteState
}
