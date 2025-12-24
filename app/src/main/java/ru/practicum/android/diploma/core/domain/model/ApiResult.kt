package ru.practicum.android.diploma.core.domain.model

sealed class ApiResult<out T> {

    data class Success<T>(val value: T) : ApiResult<T>()
    object Error : ApiResult<Nothing>()
    object NoInternet : ApiResult<Nothing>()

}
