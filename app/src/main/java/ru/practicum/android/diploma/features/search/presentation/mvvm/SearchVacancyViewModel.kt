package ru.practicum.android.diploma.features.search.presentation.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.core.domain.model.ApiResult
import ru.practicum.android.diploma.features.search.domain.entity.SearchVacancies
import ru.practicum.android.diploma.features.search.domain.entity.Vacancy
import ru.practicum.android.diploma.features.search.domain.interactor.SearchVacancyInteractor

class SearchVacancyViewModel(val interactor: SearchVacancyInteractor) : ViewModel() {
    private val stateLiveData = MutableLiveData<SearchVacancyState>()
    fun observeState(): LiveData<SearchVacancyState> = stateLiveData

    private var searchJob: Job? = null

    private var latestSearchText: String = ""

    private var currentPage: Int = 1
    private var maxPages: Int = 0
    private var searchedVacancy = SearchVacancies(
        found = 0,
        pages = 0,
        page = 0,
        items = emptyList()
    )

    fun search(changedText: String) {
        if (latestSearchText == changedText) {
            return
        }

        latestSearchText = changedText
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            renderState(SearchVacancyState.Loading)
            request(changedText)
        }
    }

    fun searchNextPage() {
        if (currentPage < maxPages) {
            currentPage++
            renderState(
                SearchVacancyState.LoadingPage(
                    SearchVacancies(
                        items = searchedVacancy.items,
                        page = currentPage,
                        pages = maxPages,
                        found = searchedVacancy.found
                    )
                )
            )
            searchJob?.cancel()
            searchJob = viewModelScope.launch {
                delay(SEARCH_DEBOUNCE_DELAY)
                request(latestSearchText)
            }
        }

    }

    private fun request(searchText: String) {
        viewModelScope.launch {
            when (val result = interactor.searchVacancies(
                mapOf(
                    "text" to searchText,
                    "page" to "$currentPage"
                )
            )) {
                ApiResult.Error -> renderState(SearchVacancyState.Error)
                ApiResult.NoInternet -> renderState(SearchVacancyState.NoInternet)
                is ApiResult.Success ->
                    if (result.value.found == EMPTY_SEARCH) {
                        renderState(SearchVacancyState.ContentEmpty)
                    } else {
                        currentPage = result.value.page
                        maxPages = result.value.pages

                        val vacancies = mutableListOf<Vacancy>()
                        vacancies.addAll(searchedVacancy.items)
                        vacancies.addAll(result.value.items)
                        searchedVacancy = searchedVacancy.copy(
                            found = result.value.found,
                            pages = result.value.pages,
                            items = vacancies,
                            page = result.value.page
                        )
                        renderState(
                            SearchVacancyState.Content(
                                searchedVacancy
                            )
                        )
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
