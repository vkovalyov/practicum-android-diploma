package ru.practicum.android.diploma.features.detail

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.features.detail.data.repository.VacancyDetailRepositoryImpl
import ru.practicum.android.diploma.features.detail.domain.interactor.VacancyDetailInteractor
import ru.practicum.android.diploma.features.detail.domain.interactor.VacancyDetailInteractorImpl
import ru.practicum.android.diploma.features.detail.domain.repository.VacancyDetailRepository
import ru.practicum.android.diploma.features.detail.presentation.mvvm.VacancyDetailViewModel

val detailModule = module {
    single<VacancyDetailRepository> { VacancyDetailRepositoryImpl(get()) }
    single<VacancyDetailInteractor> { VacancyDetailInteractorImpl(get()) }

    viewModel { (vacancyId: String) ->
        VacancyDetailViewModel(vacancyId, get())
    }
}
