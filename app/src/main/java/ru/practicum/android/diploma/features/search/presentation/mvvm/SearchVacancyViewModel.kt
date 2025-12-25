package ru.practicum.android.diploma.features.search.presentation.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.core.domain.model.ApiResult
import ru.practicum.android.diploma.features.search.domain.interactor.SearchVacancyInteractor

class SearchVacancyViewModel(val interactor: SearchVacancyInteractor) : ViewModel() {
    private val stateLiveData = MutableLiveData<SearchVacancyState>()
    fun observeState(): LiveData<SearchVacancyState> = stateLiveData

    private var searchJob: Job? = null

    private var latestSearchText: String? = null

    fun search(changedText: String) {
        if (latestSearchText == changedText) {
            return
        }

        this.latestSearchText = changedText
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            request(changedText)
        }
    }

    private fun request(searchText: String) {
        renderState(SearchVacancyState.Loading)
        viewModelScope.launch {
            when (val result = interactor.searchVacancies(mapOf("text" to searchText))) {
                ApiResult.Error -> renderState(SearchVacancyState.Error)
                ApiResult.NoInternet -> renderState(SearchVacancyState.NoInternet)
                is ApiResult.Success ->
                    if (result.value.found == EMPTY_SEARCH) {
                        renderState(SearchVacancyState.ContentEmpty)
                    } else {
                        renderState(SearchVacancyState.Content(result.value))
                    }
            }
        }
    }

    fun clear() {
        renderState(SearchVacancyState.Initial)
    }

    private fun renderState(state: SearchVacancyState) {
        stateLiveData.postValue(state)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L

        private const val EMPTY_SEARCH = 0
    }
}
