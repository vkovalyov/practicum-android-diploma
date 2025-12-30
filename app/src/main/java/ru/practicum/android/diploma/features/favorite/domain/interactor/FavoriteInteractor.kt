package ru.practicum.android.diploma.features.favorite.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.features.detail.domain.entity.VacancyDetail
import ru.practicum.android.diploma.features.favorite.domain.entity.FavoriteVacancy

interface FavoriteInteractor {
    fun getAllFavorites(): Flow<List<FavoriteVacancy>>
    suspend fun getFavoriteById(vacancyId: String): FavoriteVacancy?
    suspend fun addToFavorites(vacancy: VacancyDetail)
    suspend fun removeFromFavorites(vacancyId: String)
    fun isFavorite(vacancyId: String): Flow<Boolean>
}
