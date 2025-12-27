package ru.practicum.android.diploma.features.favorite.presentation.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.features.favorite.domain.interactor.FavoriteInteractor

class FavoriteViewModel(
    private val interactor: FavoriteInteractor
) : ViewModel() {

    private val stateLiveData = MutableLiveData<FavoriteState>(FavoriteState.Loading)
    fun observeState(): LiveData<FavoriteState> = stateLiveData

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            interactor.getAllFavorites()
                .catch {
                    stateLiveData.postValue(FavoriteState.Error)
                }
                .collect { vacancies ->
                    if (vacancies.isEmpty()) {
                        stateLiveData.postValue(FavoriteState.Empty)
                    } else {
                        stateLiveData.postValue(FavoriteState.Content(vacancies))
                    }
                }
        }
    }
}
