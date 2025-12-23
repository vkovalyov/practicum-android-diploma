package ru.practicum.android.diploma.core.data.network

import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import ru.practicum.android.diploma.core.data.dto.AreaDto
import ru.practicum.android.diploma.core.data.dto.IndustryDto
import ru.practicum.android.diploma.core.data.dto.VacancyDetail
import ru.practicum.android.diploma.core.data.dto.VacancyDto
import ru.practicum.android.diploma.core.domain.model.ApiResult
import ru.practicum.android.diploma.core.domain.repository.NetworkConnectivity

class RetrofitNetworkClient(val networkConnectivity: NetworkConnectivity) : NetworkClient {
    private val baseUrl = "https://practicum-diploma-8bc38133faba.herokuapp.com/"

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .build()

    private val retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val vacancyApi = retrofit.create<ApiService>()

    private suspend fun <T> execute(apiCall: suspend () -> Response<T>): ApiResult<T> {
        return try {
            if (!networkConnectivity.isOnline()) {
                return ApiResult.NoInternet
            }
            val response = apiCall()
            if (response.isSuccessful) {
                response.body()?.let { ApiResult.Success(it) }
                    ?: ApiResult.Error
            } else {
                ApiResult.Error
            }
        } catch (_: Exception) {
            ApiResult.Error
        }
    }

    override suspend fun getVacancyById(id: String): ApiResult<VacancyDetail> {
        return execute { vacancyApi.getVacancy(id) }
    }

    override suspend fun getIndustries(): ApiResult<List<IndustryDto>> {
        return execute { vacancyApi.getIndustries() }
    }

    override suspend fun getAreas(): ApiResult<List<AreaDto>> {
        return execute { vacancyApi.getAreas() }
    }

    override suspend fun searchVacancies(query: Map<String, Any>): ApiResult<List<VacancyDto>> {
        return execute { vacancyApi.searchVacancies(query) }
    }

}
