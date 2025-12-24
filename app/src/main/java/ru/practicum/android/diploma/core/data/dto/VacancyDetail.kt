package ru.practicum.android.diploma.core.data.dto

data class VacancyDetail(
    val id: String,
    val name: String,
    val salary: Salary?,
    val experience: Experience?,
    val schedule: Schedule?,
    val employment: Employment?,
    val employer: Employer?,
    val url: String?,
    val description: String?,
    val skills: List<String>?,
    val industry: Industry?,
)
