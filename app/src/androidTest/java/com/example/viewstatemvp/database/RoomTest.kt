package com.example.viewstatemvp.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.viewstatemvp.model.database.MusicDao
import com.example.viewstatemvp.model.database.MusicDataBase
import org.junit.After
import org.junit.Before

class RoomTest {
    lateinit var musicDataBase: MusicDataBase
    lateinit var musicDao: MusicDao

    @Before
    fun setUpDB() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        musicDataBase = Room.inMemoryDatabaseBuilder(context, MusicDataBase::class.java).build()
        musicDao = musicDataBase.getDao()
    }

    @After
    fun closeDB() {
        musicDataBase.close()
    }
}