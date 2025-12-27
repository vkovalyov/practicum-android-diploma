package ru.practicum.android.diploma.features.detail.presentation.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.core.domain.model.ApiResult
import ru.practicum.android.diploma.features.detail.domain.interactor.VacancyDetailInteractor

class VacancyDetailViewModel(
    private val vacancyId: String,
    private val interactor: VacancyDetailInteractor
) : ViewModel() {

    private val _state = MutableLiveData<VacancyDetailState>(VacancyDetailState.Loading)
    val state: LiveData<VacancyDetailState> = _state

    init {
        loadVacancy()
    }

    fun loadVacancy() {
        _state.value = VacancyDetailState.Loading
        viewModelScope.launch {
            when (val result = interactor.getVacancyById(vacancyId)) {
                is ApiResult.Success -> {
                    _state.postValue(VacancyDetailState.Content(result.value))
                }
                is ApiResult.Error -> {
                    _state.postValue(VacancyDetailState.Error)
                }
                is ApiResult.NoInternet -> {
                    _state.postValue(VacancyDetailState.NoInternet)
                }
            }
        }
    }
}
