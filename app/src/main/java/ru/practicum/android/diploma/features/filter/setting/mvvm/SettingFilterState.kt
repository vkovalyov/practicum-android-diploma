package ru.practicum.android.diploma.features.filter.setting.mvvm

import ru.practicum.android.diploma.core.domain.model.FilterPreferences

sealed interface SettingFilterState {

    data class Content(
        val filter: FilterPreferences = FilterPreferences(),
        val showSave: Boolean = false,
        val showClear: Boolean = false
    ) :
        SettingFilterState

    data object CloseScreen : SettingFilterState
}
