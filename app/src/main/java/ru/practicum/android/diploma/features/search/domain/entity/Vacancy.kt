package ru.practicum.android.diploma.features.search.domain.entity

data class Vacancy(
    val id: String,
    val name: String,
    val employer: Employer,
    val salary: Salary?
)
