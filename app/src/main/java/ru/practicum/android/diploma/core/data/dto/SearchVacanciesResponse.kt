package ru.practicum.android.diploma.core.data.dto

data class SearchVacanciesResponse(
    val found: Int,
    val pages: Int,
    val page: Int,
    val items: List<Vacancy>
)
