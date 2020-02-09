package com.example.viewstatemvp.di

import com.example.viewstatemvp.model.RemoteSource
import com.example.viewstatemvp.model.network.MusicApi
import com.example.viewstatemvp.model.network.NetworkUrl
import com.example.viewstatemvp.model.network.RemoteSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [RemoteSourceImplModule::class])
abstract class RemoteSourceModule {
    @Binds
    @Singleton
    abstract fun bindRemoteSource(remoteSourceImpl: RemoteSourceImpl): RemoteSource
}

@Module
class RemoteSourceImplModule {
    @Provides
    @Singleton
    fun provideRemoteSourceImpl(api: MusicApi): RemoteSourceImpl =
        RemoteSourceImpl(api)

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): MusicApi = retrofit.create(MusicApi::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder().baseUrl(NetworkUrl.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()


}