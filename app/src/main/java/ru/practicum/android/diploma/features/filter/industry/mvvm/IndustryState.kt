package ru.practicum.android.diploma.features.filter.industry.mvvm

import ru.practicum.android.diploma.features.filter.industry.domain.model.Industry

sealed interface IndustryState {
    data object Loading : IndustryState
    data object Error : IndustryState
    data class Content(
        val industries: List<Industry>,
        val selectedIndustry: Industry? = null,
        val searchQuery: String = ""
    ) : IndustryState
}
