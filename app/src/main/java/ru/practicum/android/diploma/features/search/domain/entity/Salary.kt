package ru.practicum.android.diploma.features.search.domain.entity

data class Salary(
    val id: String,
    val currency: String,
    val from: Int?,
    val to: Int?,
)
