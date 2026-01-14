package ru.practicum.android.diploma.features.main

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.practicum.android.diploma.core.data.cache.dataStoreModule
import ru.practicum.android.diploma.core.data.di.dbModule
import ru.practicum.android.diploma.core.data.di.networkModule
import ru.practicum.android.diploma.features.detail.detailModule
import ru.practicum.android.diploma.features.favorite.favoriteModule
import ru.practicum.android.diploma.features.filter.industry.industryModule
import ru.practicum.android.diploma.features.filter.setting.settingFilterModule
import ru.practicum.android.diploma.features.search.searchModule
import ru.practicum.android.diploma.features.team.teamModule

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(
                dataStoreModule,
                networkModule,
                dbModule,
                searchModule,
                detailModule,
                teamModule,
                favoriteModule,
                settingFilterModule,
                industryModule
            )
        }
    }
}
