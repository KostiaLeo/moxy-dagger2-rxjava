package com.example.viewstatemvp.di

import android.content.Context
import com.example.viewstatemvp.model.*
import com.example.viewstatemvp.model.network.RemoteSourceImpl
import com.example.viewstatemvp.model.network.MusicApi
import com.example.viewstatemvp.presenter.MainPresenter
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [RepositoryImplModule::class])
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindRepository(repositoryImpl: RepositoryImpl): Repository
}

@Module(includes = [RemoteSourceModule::class, LocalSourceModule::class, NetworkDispatcherModule::class])
class RepositoryImplModule {
    @Provides
    @Singleton
    fun provideRepository(
        localSource: LocalSource,
        remoteSource: RemoteSource,
        networkConnectionDispatcher: NetworkConnectionDispatcher
    ): RepositoryImpl = RepositoryImpl(localSource, remoteSource, networkConnectionDispatcher)
}

@Module
class NetworkDispatcherModule (private val context: Context) {
    @Provides
    @Singleton
    fun provideNetworkDispatcher(): NetworkConnectionDispatcher = NetworkConnectionDispatcher(context.applicationContext)
}