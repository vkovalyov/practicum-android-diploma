package ru.practicum.android.diploma.features.favorite.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.features.detail.domain.entity.VacancyDetail
import ru.practicum.android.diploma.features.favorite.domain.entity.FavoriteVacancy
import ru.practicum.android.diploma.features.favorite.domain.repository.FavoriteRepository

class FavoriteInteractorImpl(
    private val repository: FavoriteRepository
) : FavoriteInteractor {

    override fun getAllFavorites(): Flow<List<FavoriteVacancy>> {
        return repository.getAllFavorites()
    }

    override suspend fun getFavoriteById(vacancyId: String): FavoriteVacancy? {
        return repository.getFavoriteById(vacancyId)
    }

    override suspend fun addToFavorites(vacancy: VacancyDetail) {
        repository.addToFavorites(vacancy)
    }

    override suspend fun removeFromFavorites(vacancyId: String) {
        repository.removeFromFavorites(vacancyId)
    }

    override fun isFavorite(vacancyId: String): Flow<Boolean> {
        return repository.isFavorite(vacancyId)
    }
}
