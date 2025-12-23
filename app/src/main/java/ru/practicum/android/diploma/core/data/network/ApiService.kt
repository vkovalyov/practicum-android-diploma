package ru.practicum.android.diploma.core.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.core.data.dto.AreaDto
import ru.practicum.android.diploma.core.data.dto.IndustryDto
import ru.practicum.android.diploma.core.data.dto.VacancyDetail
import ru.practicum.android.diploma.core.data.dto.VacancyDto

interface ApiService {
    @GET("vacancies/{id}")
    suspend fun getVacancy(@Path("id") vacancyId: String): Response<VacancyDetail>

    @GET("areas")
    suspend fun getAreas(): Response<List<AreaDto>>

    @GET("industries")
    suspend fun getIndustries(): Response<List<IndustryDto>>

    @GET("vacancies")
    suspend fun searchVacancies(
        @QueryMap queries: Map<String, Any>
    ): Response<List<VacancyDto>>
}
