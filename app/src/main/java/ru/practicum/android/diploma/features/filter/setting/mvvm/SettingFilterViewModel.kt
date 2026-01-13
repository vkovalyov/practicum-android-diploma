package ru.practicum.android.diploma.features.filter.setting.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.core.domain.interactor.FilterInteractor
import ru.practicum.android.diploma.core.domain.model.FilterPreferences
import ru.practicum.android.diploma.core.domain.model.filterIsClear

class SettingFilterViewModel(val interactor: FilterInteractor) : ViewModel() {
    private val stateLiveData = MutableLiveData<SettingFilterState>()
    fun observeState(): LiveData<SettingFilterState> = stateLiveData

    var initFilter: FilterPreferences = FilterPreferences()
    var filterPreferences: FilterPreferences = FilterPreferences()

    init {
        viewModelScope.launch {
            interactor.filter.collect { value ->
                initFilter =
                    FilterPreferences(
                        salary = value?.salary,
                        areaId = value?.areaId,
                        industryId = value?.industryId,
                        withoutSalaries = value?.withoutSalaries
                    )
                filterPreferences = initFilter
                renderAndCheck()

            }
        }
    }

    fun saveChanges() {
        viewModelScope.launch {
            save()
        }
    }

    fun clearFilter() {
        viewModelScope.launch {
            clear()
        }
    }

    suspend fun save() {
        interactor.save(filterPreferences)
        renderState(SettingFilterState.CloseScreen)
    }

    fun clear() {
        filterPreferences = FilterPreferences()
        renderAndCheck()
    }

    fun changeSalary(value: String?) {
        filterPreferences = filterPreferences.copy(salary = value)
        renderAndCheck()
    }

    fun changeWithoutSalaries(value: Boolean) {
        val withoutSalaries = if (value) true else null
        filterPreferences = filterPreferences.copy(withoutSalaries = withoutSalaries)
        renderAndCheck()
    }

    fun renderAndCheck() {
        var showSave = false
        var showClear = false

        if (!filterPreferences.filterIsClear()) {
            showClear = true
        }

        if (initFilter != filterPreferences) {
            showSave = true
        }
        renderState(SettingFilterState.Content(filterPreferences, showClear = showClear, showSave = showSave))
    }

    private fun renderState(state: SettingFilterState) {
        stateLiveData.postValue(state)
    }
}
