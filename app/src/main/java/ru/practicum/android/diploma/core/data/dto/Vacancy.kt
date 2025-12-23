package ru.practicum.android.diploma.core.data.dto

data class VacancyDto(
    val id: String,
    val name: String,
    val salary: SalaryDto?,
    val employer: EmployerDto?,
)
