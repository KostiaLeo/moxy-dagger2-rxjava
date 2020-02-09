package com.example.viewstatemvp.di

import com.example.viewstatemvp.model.Repository
import com.example.viewstatemvp.presenter.MainPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [RepositoryModule::class])
class PresenterModule {
    @Provides
    @Singleton
    fun providePresenter(repository: Repository): MainPresenter = MainPresenter(repository)
}