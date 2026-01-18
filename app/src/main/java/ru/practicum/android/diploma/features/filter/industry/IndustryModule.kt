package ru.practicum.android.diploma.features.filter.industry

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.features.filter.industry.mvvm.IndustryViewModel

val industryModule = module {
    viewModel {
        IndustryViewModel(get(), get())
    }
}
