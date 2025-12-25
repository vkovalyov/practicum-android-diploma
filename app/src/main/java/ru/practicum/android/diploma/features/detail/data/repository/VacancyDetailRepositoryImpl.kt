package ru.practicum.android.diploma.features.detail.data.repository

import ru.practicum.android.diploma.core.data.network.NetworkClient
import ru.practicum.android.diploma.core.domain.model.ApiResult
import ru.practicum.android.diploma.features.detail.domain.entity.Address
import ru.practicum.android.diploma.features.detail.domain.entity.Contacts
import ru.practicum.android.diploma.features.detail.domain.entity.Employer
import ru.practicum.android.diploma.features.detail.domain.entity.Salary
import ru.practicum.android.diploma.features.detail.domain.entity.VacancyDetail
import ru.practicum.android.diploma.features.detail.domain.repository.VacancyDetailRepository

class VacancyDetailRepositoryImpl(
    private val networkClient: NetworkClient
) : VacancyDetailRepository {

    override suspend fun getVacancyById(id: String): ApiResult<VacancyDetail> {
        return when (val response = networkClient.getVacancyById(id)) {
            is ApiResult.Success -> {
                val dto = response.value
                ApiResult.Success(
                    VacancyDetail(
                        id = dto.id,
                        name = dto.name,
                        salary = dto.salary?.let {
                            Salary(
                                from = it.from,
                                to = it.to,
                                currency = it.currency
                            )
                        },
                        address = dto.address?.let {
                            Address(
                                city = it.city,
                                street = it.street,
                                building = it.building,
                                fullAddress = it.fullAddress
                            )
                        },
                        experience = dto.experience?.name,
                        schedule = dto.schedule?.name,
                        employment = dto.employment?.name,
                        contacts = dto.contacts?.let { contacts ->
                            Contacts(
                                name = contacts.name,
                                email = contacts.email,
                                phones = contacts.phone
                            )
                        },
                        employer = dto.employer?.let {
                            Employer(
                                id = it.id,
                                name = it.name,
                                logo = it.logo
                            )
                        },
                        areaName = dto.area?.name,
                        url = dto.url,
                        description = dto.description,
                        skills = dto.skills
                    )
                )
            }
            is ApiResult.Error -> ApiResult.Error
            is ApiResult.NoInternet -> ApiResult.NoInternet
        }
    }
}
