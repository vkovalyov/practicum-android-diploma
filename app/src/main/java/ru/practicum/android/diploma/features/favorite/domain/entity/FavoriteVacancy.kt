package ru.practicum.android.diploma.features.favorite.domain.entity

import ru.practicum.android.diploma.features.search.domain.entity.Salary

data class FavoriteVacancy(
    val id: String,
    val name: String,
    val employerName: String,
    val employerLogoUrl: String?,
    val areaName: String?,
    val salary: Salary?
)
