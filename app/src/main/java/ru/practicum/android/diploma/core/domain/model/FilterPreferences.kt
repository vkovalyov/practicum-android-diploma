package ru.practicum.android.diploma.core.domain.model

data class FilterPreferences(
    val withoutSalaries: Boolean? = null,
    val salary: String? = null,
    val industryId: String? = null,
    val industryName: String? = null,
    val areaId: Int? = null
)

fun FilterPreferences.filterIsClear(): Boolean {
    return !(salary != null ||
        areaId != null ||
        industryId != null ||
        industryName != null ||
        withoutSalaries != null)
}
