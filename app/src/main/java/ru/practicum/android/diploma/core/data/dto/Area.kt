package ru.practicum.android.diploma.core.data.dto

data class AreaDto(
    val id: String,
    val name: String,
    val parentId: String,
    val areas: List<AreaDto>
)
