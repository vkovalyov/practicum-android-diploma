package ru.practicum.android.diploma.core.data.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.core.data.db.AppDatabase

val dbModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "database.db"
        ).build()
    }

    single { get<AppDatabase>().favoriteVacancyDao() }
}
