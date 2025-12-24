package ru.practicum.android.diploma.features.main

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.practicum.android.diploma.core.data.di.dbModule
import ru.practicum.android.diploma.core.data.di.networkModule
import ru.practicum.android.diploma.features.search.searchModule

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(
                networkModule,
                dbModule,
                searchModule,
            )
        }
    }
}
