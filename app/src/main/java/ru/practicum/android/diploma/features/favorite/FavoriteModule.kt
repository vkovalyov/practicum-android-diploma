package ru.practicum.android.diploma.features.favorite

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.features.favorite.data.repository.FavoriteRepositoryImpl
import ru.practicum.android.diploma.features.favorite.domain.interactor.FavoriteInteractor
import ru.practicum.android.diploma.features.favorite.domain.interactor.FavoriteInteractorImpl
import ru.practicum.android.diploma.features.favorite.domain.repository.FavoriteRepository
import ru.practicum.android.diploma.features.favorite.presentation.mvvm.FavoriteViewModel

val favoriteModule = module {
    single<FavoriteRepository> { FavoriteRepositoryImpl(get()) }
    single<FavoriteInteractor> { FavoriteInteractorImpl(get()) }

    viewModel {
        FavoriteViewModel(get())
    }
}
