package ru.practicum.android.diploma.core.data.dto.filter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Filter(
    val withoutSalaries: Boolean? = null,
    val salary: String? = null,
    val industryId: Int? = null,
    val areaId: Int? = null
) : Parcelable
