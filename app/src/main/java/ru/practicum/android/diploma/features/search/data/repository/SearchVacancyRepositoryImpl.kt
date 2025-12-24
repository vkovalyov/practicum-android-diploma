package ru.practicum.android.diploma.features.search.data.repository

import ru.practicum.android.diploma.core.data.network.NetworkClient
import ru.practicum.android.diploma.core.domain.model.ApiResult
import ru.practicum.android.diploma.features.search.domain.entity.Employer
import ru.practicum.android.diploma.features.search.domain.entity.Salary
import ru.practicum.android.diploma.features.search.domain.entity.SearchVacancies
import ru.practicum.android.diploma.features.search.domain.entity.Vacancy
import ru.practicum.android.diploma.features.search.domain.repository.SearchVacancyRepository

class SearchVacancyRepositoryImpl(private val networkClient: NetworkClient) : SearchVacancyRepository {

    override suspend fun searchVacancies(query: Map<String, String>): ApiResult<SearchVacancies> {
        return when (val response = networkClient.searchVacancies(query)) {
            is ApiResult.Success -> {

                ApiResult.Success(
                    SearchVacancies(
                        found = response.value.found,
                        pages = response.value.pages,
                        page = response.value.page,
                        items = response.value.items.map {
                            Vacancy(
                                id = it.id,
                                name = it.name,
                                salary = if (it.salary == null) null else Salary(
                                    id = it.salary.id,
                                    it.salary.currency,
                                    it.salary.from,
                                    it.salary.to
                                ),
                                employer = Employer(
                                    it.employer.id,
                                    it.employer.name,
                                    it.employer.logo
                                )
                            )
                        }
                    )
                )
            }

            is ApiResult.Error -> ApiResult.Error
            is ApiResult.NoInternet -> ApiResult.NoInternet
        }
    }
}
