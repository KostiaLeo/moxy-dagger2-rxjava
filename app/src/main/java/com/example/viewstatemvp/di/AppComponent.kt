package com.example.viewstatemvp.di

import com.example.viewstatemvp.presenter.MainPresenter
import com.example.viewstatemvp.view.MainActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ModelImplModule::class, ModelModule::class, PresenterModule::class, NetworkModule::class])
@Singleton
interface AppComponent {
    fun providePresenter(): MainPresenter
    fun inject(mainActivity: MainActivity)
}