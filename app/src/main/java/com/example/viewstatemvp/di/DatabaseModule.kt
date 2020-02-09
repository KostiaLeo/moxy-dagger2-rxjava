package com.example.viewstatemvp.di

import android.content.Context
import androidx.room.Room
import com.example.viewstatemvp.model.LocalSource
import com.example.viewstatemvp.model.database.LocalSourceImpl
import com.example.viewstatemvp.model.database.MusicDao
import com.example.viewstatemvp.model.database.MusicDataBase
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [LocalSourceImplModule::class])
abstract class LocalSourceModule {

    @Binds
    @Singleton
    abstract fun bindLocalSource(localSourceImpl: LocalSourceImpl): LocalSource
}

@Module
class LocalSourceImplModule(val context: Context) {
    companion object {
        const val databaseName = "music_database"
    }

    @Provides
    @Singleton
    fun provideDataBase(): MusicDataBase =
        Room.databaseBuilder(
            context.applicationContext,
            MusicDataBase::class.java,
            databaseName
        ).fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideDao(dataBase: MusicDataBase): MusicDao = dataBase.getDao()

    @Provides
    @Singleton
    fun provideLocalSourceImpl(dao: MusicDao): LocalSourceImpl = LocalSourceImpl(dao)
}