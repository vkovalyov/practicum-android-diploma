package ru.practicum.android.diploma.core.utils

import retrofit2.Response
import ru.practicum.android.diploma.core.domain.model.ApiResult
import ru.practicum.android.diploma.core.domain.repository.NetworkConnectivity

suspend fun <T> handleApiCall(
    networkConnectivity: NetworkConnectivity,
    apiCall: suspend () -> Response<T>
): ApiResult<T> {
    if (!networkConnectivity.isOnline()) {
        return ApiResult.NoInternet
    }

    return try {
        val response = apiCall()

        if (response.isSuccessful) {
            response.body()?.let { data ->
                ApiResult.Success(value = data)
            } ?: ApiResult.Error
        } else {
            ApiResult.Error
        }
    } catch (_: Exception) {
        ApiResult.Error
    }
}
