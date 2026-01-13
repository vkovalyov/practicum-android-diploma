package ru.practicum.android.diploma.core.domain.model

data class FilterPreferences(
    val withoutSalaries: Boolean? = null,
    val salary: String? = null,
    val industryId: Int? = null,
    val areaId: Int? = null
) {
    fun filterIsClear(): Boolean {
        return !(salary != null ||
                areaId != null ||
                industryId != null ||
                withoutSalaries != null)
    }
}
