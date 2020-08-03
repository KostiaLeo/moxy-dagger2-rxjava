package com.example.viewstatemvp.di

import android.content.Context
import com.example.viewstatemvp.model.Repository
import com.example.viewstatemvp.presenter.MainPresenter
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [RepositoryModule::class])
class PresenterModule(private val context: Context) {
    @Provides
    @Reusable
    @Named("WorkScheduler")
    fun providesWorkScheduler(): Scheduler = Schedulers.io()

    @Provides
    @Reusable
    @Named("ResultScheduler")
    fun providesResultScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Provides
    @Singleton
    fun providePresenter(
        repository: Repository,
        @Named("WorkScheduler") workScheduler: Scheduler,
        @Named("ResultScheduler") resultScheduler: Scheduler
    ): MainPresenter = MainPresenter(repository, context.applicationContext, workScheduler, resultScheduler)
}