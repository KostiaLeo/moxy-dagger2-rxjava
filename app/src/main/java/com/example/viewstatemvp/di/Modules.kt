package com.example.viewstatemvp.di

import com.example.viewstatemvp.model.Model
import com.example.viewstatemvp.model.ModelImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module(includes = [ModelImplModule::class])
abstract class ModelModule {
    @Binds
    @Singleton
    abstract fun bindModel(modelImpl: ModelImpl): Model
}

@Module
class ModelImplModule {
    @Provides
    @Singleton
    fun provideModelImpl(): ModelImpl = ModelImpl()
}