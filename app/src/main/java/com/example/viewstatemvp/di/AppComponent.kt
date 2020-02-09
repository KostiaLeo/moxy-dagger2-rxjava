package com.example.viewstatemvp.di

import com.example.viewstatemvp.view.MainActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [
    LocalSourceImplModule::class, LocalSourceModule::class,
    RemoteSourceImplModule::class, RemoteSourceModule::class,
    RepositoryImplModule::class, RepositoryModule::class,
    NetworkDispatcherModule::class,
    PresenterModule::class
])
@Singleton
interface AppComponent {
    fun inject(activity: MainActivity)
}