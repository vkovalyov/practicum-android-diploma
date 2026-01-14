package ru.practicum.android.diploma.features.filter.industry.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.core.data.network.NetworkClient
import ru.practicum.android.diploma.core.domain.interactor.FilterInteractor
import ru.practicum.android.diploma.core.domain.model.ApiResult
import ru.practicum.android.diploma.core.domain.model.FilterPreferences
import ru.practicum.android.diploma.features.filter.industry.domain.model.Industry

class IndustryViewModel(
    private val filterInteractor: FilterInteractor,
    private val networkClient: NetworkClient
) : ViewModel() {

    private val stateLiveData = MutableLiveData<IndustryState>()
    fun observeState(): LiveData<IndustryState> = stateLiveData

    private var allIndustries: List<Industry> = emptyList()
    private var selectedIndustry: Industry? = null
    private var searchQuery: String = ""
    private var currentFilter: FilterPreferences = FilterPreferences()

    init {
        loadIndustries()
    }

    private fun loadIndustries() {
        stateLiveData.postValue(IndustryState.Loading)
        viewModelScope.launch {
            val filter = filterInteractor.filter.first()
            currentFilter = filter ?: FilterPreferences()

            when (val result = networkClient.getIndustries()) {
                is ApiResult.Success -> {
                    allIndustries = result.value.map { Industry(it.id, it.name) }
                    selectedIndustry = filter?.industryId?.let { id ->
                        allIndustries.find { it.id == id }
                    }
                    renderContent()
                }
                is ApiResult.Error, is ApiResult.NoInternet -> {
                    stateLiveData.postValue(IndustryState.Error)
                }
            }
        }
    }

    fun search(query: String) {
        searchQuery = query
        renderContent()
    }

    fun selectIndustry(industry: Industry) {
        selectedIndustry = if (selectedIndustry?.id == industry.id) {
            null
        } else {
            industry
        }
        renderContent()
    }

    fun applySelection(onComplete: () -> Unit) {
        viewModelScope.launch {
            val updatedFilter = currentFilter.copy(
                industryId = selectedIndustry?.id,
                industryName = selectedIndustry?.name
            )
            filterInteractor.save(updatedFilter)
            onComplete()
        }
    }

    private fun renderContent() {
        val filteredIndustries = if (searchQuery.isEmpty()) {
            allIndustries
        } else {
            allIndustries.filter {
                it.name.contains(searchQuery, ignoreCase = true)
            }
        }
        stateLiveData.postValue(
            IndustryState.Content(
                industries = filteredIndustries,
                selectedIndustry = selectedIndustry,
                searchQuery = searchQuery
            )
        )
    }
}
