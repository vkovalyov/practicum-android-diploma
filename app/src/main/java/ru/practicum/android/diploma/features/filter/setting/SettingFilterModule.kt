package ru.practicum.android.diploma.features.filter.setting

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.features.filter.setting.mvvm.SettingFilterViewModel

val settingFilterModule = module {

    viewModel {
        SettingFilterViewModel(get())
    }
}
