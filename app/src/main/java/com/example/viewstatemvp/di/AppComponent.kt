package com.example.viewstatemvp.di

import android.app.Application
import com.example.viewstatemvp.model.ModelImpl
import com.example.viewstatemvp.presenter.MainPresenter
import com.example.viewstatemvp.view.MainActivity
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Singleton

@Component(modules = [MainModule::class, ModelImplProvider::class, PresenterModule::class])
@Singleton
interface AppComponent {

    fun inject(activity: MainActivity)

}