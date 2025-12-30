package ru.practicum.android.diploma.features.detail.presentation.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.core.domain.model.ApiResult
import ru.practicum.android.diploma.features.detail.domain.entity.VacancyDetail
import ru.practicum.android.diploma.features.detail.domain.interactor.VacancyDetailInteractor
import ru.practicum.android.diploma.features.favorite.domain.interactor.FavoriteInteractor

class VacancyDetailViewModel(
    private val vacancyId: String,
    private val detailInteractor: VacancyDetailInteractor,
    private val favoriteInteractor: FavoriteInteractor
) : ViewModel() {

    private val _state = MutableLiveData<VacancyDetailState>(VacancyDetailState.Loading)
    val state: LiveData<VacancyDetailState> = _state

    private val _isFavorite = MutableLiveData(false)
    val isFavorite: LiveData<Boolean> = _isFavorite

    init {
        loadVacancy()
        observeFavoriteStatus()
    }

    fun loadVacancy() {
        _state.value = VacancyDetailState.Loading
        viewModelScope.launch {
            when (val result = detailInteractor.getVacancyById(vacancyId)) {
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

    private fun observeFavoriteStatus() {
        viewModelScope.launch {
            favoriteInteractor.isFavorite(vacancyId).collect { isFav ->
                _isFavorite.postValue(isFav)
            }
        }
    }

    fun toggleFavorite(vacancy: VacancyDetail) {
        viewModelScope.launch {
            if (_isFavorite.value == true) {
                favoriteInteractor.removeFromFavorites(vacancy.id)
            } else {
                favoriteInteractor.addToFavorites(vacancy)
            }
        }
    }
}
