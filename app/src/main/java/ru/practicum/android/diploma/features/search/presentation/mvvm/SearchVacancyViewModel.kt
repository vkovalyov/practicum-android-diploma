package ru.practicum.android.diploma.features.search.presentation.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.core.domain.interactor.FilterInteractor
import ru.practicum.android.diploma.core.domain.model.ApiResult
import ru.practicum.android.diploma.core.domain.model.FilterPreferences
import ru.practicum.android.diploma.features.search.domain.entity.SearchVacancies
import ru.practicum.android.diploma.features.search.domain.entity.Vacancy
import ru.practicum.android.diploma.features.search.domain.interactor.SearchVacancyInteractor

class SearchVacancyViewModel(
    val interactor: SearchVacancyInteractor,
    val filterInteractor: FilterInteractor
) : ViewModel() {
    private val stateLiveData = MutableLiveData<SearchVacancyState>(SearchVacancyState.Initial(searchText = ""))
    fun observeState(): LiveData<SearchVacancyState> = stateLiveData

    private val stateFilterLiveData = MutableLiveData<FilterPreferences?>()
    fun observeFilterState(): LiveData<FilterPreferences?> = stateFilterLiveData

    private var searchJob: Job? = null

    private var latestSearchText: String = ""

    private var currentPage: Int = START_PAGE
    private var maxPages: Int = 0
    private var searchedVacancy = SearchVacancies(
        found = 0,
        pages = 0,
        page = 0,
        items = emptyList()
    )

    init {
        viewModelScope.launch {
            filterInteractor.filter.collect { value ->
                stateFilterLiveData.postValue(value)
                val searchText = latestSearchText
                latestSearchText = ""
                search(searchText)
            }
        }
    }

    fun search(changedText: String) {
        if (latestSearchText == changedText) {
            return
        }

        currentPage = START_PAGE
        searchedVacancy = searchedVacancy.copy(found = EMPTY_SEARCH, items = emptyList(), page = START_PAGE)
        latestSearchText = changedText
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            renderState(SearchVacancyState.Loading(searchText = latestSearchText))
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
                        found = searchedVacancy.found,
                    ),
                    searchText = latestSearchText
                )
            )
            searchJob?.cancel()
            searchJob = viewModelScope.launch {
                delay(SEARCH_DEBOUNCE_DELAY)
                request(latestSearchText)
            }
        }

    }

    fun buildParams(
        searchText: String,
    ): Map<String, String> {
        val withoutSalary = stateFilterLiveData.value?.withoutSalaries
        val salary = stateFilterLiveData.value?.salary
        val industryId = stateFilterLiveData.value?.industryId

        return mutableMapOf<String, String>().apply {
            put("text", searchText)
            put("page", "$currentPage")
            withoutSalary?.let {
                put(
                    "only_with_salary",
                    "$withoutSalary"
                )
            }
            salary?.let {
                put(
                    "salary",
                    salary
                )
            }
            industryId?.let {
                put(
                    "industry",
                    industryId
                )
            }
        }.toMap()
    }

    private fun request(searchText: String) {
        viewModelScope.launch {
            val params = buildParams(searchText)

            when (val result = interactor.searchVacancies(
                params
            )) {
                ApiResult.Error -> renderState(SearchVacancyState.Error(searchText = latestSearchText))
                ApiResult.NoInternet -> renderState(SearchVacancyState.NoInternet(searchText = latestSearchText))
                is ApiResult.Success ->
                    if (result.value.found == EMPTY_SEARCH) {
                        renderState(SearchVacancyState.ContentEmpty(searchText = latestSearchText))
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
                                searchedVacancy,
                                searchText = latestSearchText
                            )
                        )
                    }
            }
        }
    }

    fun clear() {
        renderState(SearchVacancyState.Initial(searchText = latestSearchText))
    }

    private fun renderState(state: SearchVacancyState) {
        stateLiveData.postValue(state)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L

        private const val EMPTY_SEARCH = 0

        private const val START_PAGE = 1
    }
}
