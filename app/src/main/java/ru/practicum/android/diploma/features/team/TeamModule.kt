package ru.practicum.android.diploma.features.team

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.features.team.presentation.mvvm.TeamViewModel

val teamModule = module {

    viewModel {
        TeamViewModel()
    }
}
