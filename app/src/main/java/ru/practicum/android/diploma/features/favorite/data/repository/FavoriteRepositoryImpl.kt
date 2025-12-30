package ru.practicum.android.diploma.features.favorite.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.core.data.db.FavoriteVacancyDao
import ru.practicum.android.diploma.core.data.db.FavoriteVacancyEntity
import ru.practicum.android.diploma.features.detail.domain.entity.VacancyDetail
import ru.practicum.android.diploma.features.favorite.domain.entity.FavoriteVacancy
import ru.practicum.android.diploma.features.favorite.domain.repository.FavoriteRepository
import ru.practicum.android.diploma.features.search.domain.entity.Salary

class FavoriteRepositoryImpl(
    private val favoriteVacancyDao: FavoriteVacancyDao
) : FavoriteRepository {

    override fun getAllFavorites(): Flow<List<FavoriteVacancy>> {
        return favoriteVacancyDao.getAllVacancies().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getFavoriteById(vacancyId: String): FavoriteVacancy? {
        return favoriteVacancyDao.getVacancyById(vacancyId)?.toDomain()
    }

    override suspend fun addToFavorites(vacancy: VacancyDetail) {
        favoriteVacancyDao.insertVacancy(vacancy.toEntity())
    }

    override suspend fun removeFromFavorites(vacancyId: String) {
        favoriteVacancyDao.deleteVacancyById(vacancyId)
    }

    override fun isFavorite(vacancyId: String): Flow<Boolean> {
        return favoriteVacancyDao.isVacancyFavorite(vacancyId)
    }

    private fun FavoriteVacancyEntity.toDomain(): FavoriteVacancy {
        val salary = if (salaryFrom != null || salaryTo != null) {
            Salary(
                id = id,
                currency = salaryCurrency ?: "",
                from = salaryFrom,
                to = salaryTo
            )
        } else {
            null
        }

        return FavoriteVacancy(
            id = id,
            name = name,
            employerName = employerName ?: "",
            employerLogoUrl = employerLogoUrl,
            areaName = areaName,
            salary = salary
        )
    }

    private fun VacancyDetail.toEntity() = FavoriteVacancyEntity(
        id = id,
        name = name,
        employerName = employer?.name,
        employerLogoUrl = employer?.logo,
        areaName = areaName,
        salaryFrom = salary?.from,
        salaryTo = salary?.to,
        salaryCurrency = salary?.currency,
        experience = experience,
        employment = employment,
        schedule = schedule,
        description = description,
        keySkills = skills?.joinToString(", "),
        alternateUrl = url,
        addressCity = address?.city,
        addressStreet = address?.street,
        addressBuilding = address?.building,
        addressFull = address?.fullAddress,
        contactName = contacts?.name,
        contactEmail = contacts?.email,
        contactPhones = contacts?.phones?.joinToString(";")
    )
}
