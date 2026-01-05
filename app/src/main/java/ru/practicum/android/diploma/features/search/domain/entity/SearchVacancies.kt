package ru.practicum.android.diploma.features.search.domain.entity

data class SearchVacancies(
    val found: Int,
    val pages: Int,
    val page: Int,
    val items: List<Vacancy>
)
