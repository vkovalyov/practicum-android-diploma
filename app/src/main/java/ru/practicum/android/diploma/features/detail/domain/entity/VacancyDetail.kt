package ru.practicum.android.diploma.features.detail.domain.entity

data class VacancyDetail(
    val id: String,
    val name: String,
    val salary: Salary?,
    val address: Address?,
    val experience: String?,
    val schedule: String?,
    val employment: String?,
    val contacts: Contacts?,
    val employer: Employer?,
    val areaName: String?,
    val url: String?,
    val description: String?,
    val skills: List<String>?,
)

data class Salary(
    val from: Int?,
    val to: Int?,
    val currency: String?
)

data class Address(
    val city: String?,
    val street: String?,
    val building: String?,
    val fullAddress: String?
)

data class Contacts(
    val name: String?,
    val email: String?,
    val phones: List<String>?
)

data class Employer(
    val id: String,
    val name: String,
    val logo: String?
)
