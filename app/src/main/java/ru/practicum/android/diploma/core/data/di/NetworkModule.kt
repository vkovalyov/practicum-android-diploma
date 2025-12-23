package ru.practicum.android.diploma.core.data.di

import org.koin.dsl.module
import ru.practicum.android.diploma.core.data.network.NetworkClient
import ru.practicum.android.diploma.core.data.network.NetworkConnectivityImpl
import ru.practicum.android.diploma.core.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.core.domain.repository.NetworkConnectivity

val networkModule = module {

    single<NetworkConnectivity> { NetworkConnectivityImpl(get()) }

    single<NetworkClient> { RetrofitNetworkClient(get()) }

}
