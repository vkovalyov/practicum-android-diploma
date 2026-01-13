package ru.practicum.android.diploma.core.data.cache

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.gson.Gson
import org.koin.dsl.module
import ru.practicum.android.diploma.core.data.dto.filter.Filter
import ru.practicum.android.diploma.core.data.repository.FilterRepositoryImpl
import ru.practicum.android.diploma.core.domain.interactor.FilterInteractor
import ru.practicum.android.diploma.core.domain.interactor.FilterInteractorImpl
import ru.practicum.android.diploma.core.domain.repository.FilterRepository

val dataStoreModule = module {
    single { Gson() }

    single<DataStore<Preferences>> {
        createAppPreferencesDataStore(get())
    }

    single<StorageClient<Filter>> {
        DataStorageClient(
            dataStore = get(),
            gson = get(),
            dataKey = "filter",
            type = Filter()
        )
    }

    single<FilterRepository> {
        FilterRepositoryImpl(store = get())
    }

    single<FilterInteractor> {
        FilterInteractorImpl(repository = get())
    }
}
