package ru.practicum.android.diploma.core.data.dto

data class VacancyDetail(
    val id: String,
    val name: String,
    val salary: SalaryDto?,
    val experience: ExperienceDto?,
    val schedule: ScheduleDto?,
    val employment: EmploymentDto?,
    val employer: EmployerDto?,
    val url: String?,
    val description: String?,
    val skills: List<String>?,
    val industry: IndustryDto?,
)


