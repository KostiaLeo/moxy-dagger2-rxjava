package com.example.viewstatemvp.di

import com.example.viewstatemvp.model.Model
import com.example.viewstatemvp.model.ModelImpl
import com.example.viewstatemvp.presenter.MainPresenter
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [MainModule::class])
class PresenterModule{
    @Provides
    @Singleton
    fun providePresenter(model: Model): MainPresenter = MainPresenter(model)
}

@Module(includes = [ModelImplProvider::class])
abstract class MainModule {
    @Binds
    @Singleton
    abstract fun bindModel(modelImpl: ModelImpl): Model
}

@Module
class ModelImplProvider {
    @Provides
    @Singleton
    fun provideModelImpl(): ModelImpl = ModelImpl()
}
