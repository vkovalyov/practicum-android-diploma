package ru.practicum.android.diploma.core.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_vacancies")
data class FavoriteVacancyEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val employerName: String?,
    val employerLogoUrl: String?,
    val areaName: String?,
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val salaryCurrency: String?,
    val experience: String?,
    val employment: String?,
    val schedule: String?,
    val description: String?,
    val keySkills: String?,
    val alternateUrl: String?,
    val addedAt: Long = System.currentTimeMillis()
)
