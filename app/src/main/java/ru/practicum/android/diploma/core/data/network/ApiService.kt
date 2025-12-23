package ru.practicum.android.diploma.core.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.core.data.dto.Area
import ru.practicum.android.diploma.core.data.dto.Industry
import ru.practicum.android.diploma.core.data.dto.Vacancy
import ru.practicum.android.diploma.core.data.dto.VacancyDetail

interface ApiService {
    @GET("vacancies/{id}")
    suspend fun getVacancy(@Path("id") vacancyId: String): Response<VacancyDetail>

    @GET("areas")
    suspend fun getAreas(): Response<List<Area>>

    @GET("industries")
    suspend fun getIndustries(): Response<List<Industry>>

    @GET("vacancies")
    suspend fun searchVacancies(
        @QueryMap queries: Map<String, Any>
    ): Response<List<Vacancy>>
}
