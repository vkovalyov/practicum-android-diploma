package ru.practicum.android.diploma.features.search

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.features.search.data.repository.SearchVacancyRepositoryImpl
import ru.practicum.android.diploma.features.search.domain.interactor.SearchVacancyInteractor
import ru.practicum.android.diploma.features.search.domain.interactor.SearchVacancyInteractorImpl
import ru.practicum.android.diploma.features.search.domain.repository.SearchVacancyRepository
import ru.practicum.android.diploma.features.search.presentation.mvvm.SearchVacancyViewModel

val searchModule = module {
    single<SearchVacancyRepository> { SearchVacancyRepositoryImpl(get()) }
    single<SearchVacancyInteractor> { SearchVacancyInteractorImpl(get()) }

    viewModel {
        SearchVacancyViewModel(get())
    }
}
