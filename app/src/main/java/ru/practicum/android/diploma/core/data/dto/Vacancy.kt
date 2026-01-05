package ru.practicum.android.diploma.core.data.dto

data class Vacancy(
    val id: String,
    val name: String,
    val salary: Salary?,
    val employer: Employer,
)
