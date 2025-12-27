package ru.practicum.android.diploma.features.detail.data.repository

import ru.practicum.android.diploma.core.data.db.FavoriteVacancyDao
import ru.practicum.android.diploma.core.data.db.FavoriteVacancyEntity
import ru.practicum.android.diploma.core.data.network.NetworkClient
import ru.practicum.android.diploma.core.domain.model.ApiResult
import ru.practicum.android.diploma.features.detail.domain.entity.Address
import ru.practicum.android.diploma.features.detail.domain.entity.Contacts
import ru.practicum.android.diploma.features.detail.domain.entity.Employer
import ru.practicum.android.diploma.features.detail.domain.entity.Salary
import ru.practicum.android.diploma.features.detail.domain.entity.VacancyDetail
import ru.practicum.android.diploma.features.detail.domain.repository.VacancyDetailRepository

class VacancyDetailRepositoryImpl(
    private val networkClient: NetworkClient,
    private val favoriteVacancyDao: FavoriteVacancyDao
) : VacancyDetailRepository {

    override suspend fun getVacancyById(id: String): ApiResult<VacancyDetail> {
        return when (val response = networkClient.getVacancyById(id)) {
            is ApiResult.Success -> ApiResult.Success(mapToEntity(response.value))
            is ApiResult.Error -> tryGetFromCache(id) ?: ApiResult.Error
            is ApiResult.NoInternet -> tryGetFromCache(id) ?: ApiResult.NoInternet
        }
    }

    private suspend fun tryGetFromCache(id: String): ApiResult<VacancyDetail>? {
        val cached = favoriteVacancyDao.getVacancyById(id)
        return cached?.let { ApiResult.Success(it.toDomain()) }
    }

    private fun FavoriteVacancyEntity.toDomain(): VacancyDetail {
        val hasAddress = addressCity != null || addressStreet != null ||
            addressBuilding != null || addressFull != null
        val hasContacts = contactName != null || contactEmail != null || contactPhones != null

        return VacancyDetail(
            id = id,
            name = name,
            salary = if (salaryFrom != null || salaryTo != null) {
                Salary(from = salaryFrom, to = salaryTo, currency = salaryCurrency)
            } else null,
            address = if (hasAddress) {
                Address(
                    city = addressCity,
                    street = addressStreet,
                    building = addressBuilding,
                    fullAddress = addressFull
                )
            } else null,
            experience = experience,
            schedule = schedule,
            employment = employment,
            contacts = if (hasContacts) {
                Contacts(
                    name = contactName,
                    email = contactEmail,
                    phones = contactPhones?.split(";")?.filter { it.isNotBlank() }
                )
            } else null,
            employer = employerName?.let { Employer(id = "", name = it, logo = employerLogoUrl) },
            areaName = areaName,
            url = alternateUrl,
            description = description,
            skills = keySkills?.split(", ")?.filter { it.isNotBlank() }
        )
    }

    private fun mapToEntity(dto: ru.practicum.android.diploma.core.data.dto.VacancyDetail) = VacancyDetail(
        id = dto.id,
        name = dto.name,
        salary = dto.salary?.let { Salary(from = it.from, to = it.to, currency = it.currency) },
        address = dto.address?.let {
            Address(city = it.city, street = it.street, building = it.building, fullAddress = it.fullAddress)
        },
        experience = dto.experience?.name,
        schedule = dto.schedule?.name,
        employment = dto.employment?.name,
        contacts = dto.contacts?.let { Contacts(name = it.name, email = it.email, phones = it.phone) },
        employer = dto.employer?.let { Employer(id = it.id, name = it.name, logo = it.logo) },
        areaName = dto.area?.name,
        url = dto.url,
        description = dto.description,
        skills = dto.skills
    )
}
