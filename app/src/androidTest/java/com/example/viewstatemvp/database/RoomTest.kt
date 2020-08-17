package com.example.viewstatemvp.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.viewstatemvp.model.Currency
import com.example.viewstatemvp.model.Image
import com.example.viewstatemvp.model.Results
import com.example.viewstatemvp.model.database.MusicDao
import com.example.viewstatemvp.model.database.MusicDataBase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.TestSubscriber

@RunWith(AndroidJUnit4::class)
class RoomTest {
    lateinit var musicDataBase: MusicDataBase
    lateinit var musicDao: MusicDao

    @Before
    fun setUpDB() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        musicDataBase = Room.inMemoryDatabaseBuilder(context, MusicDataBase::class.java).build()
        musicDao = musicDataBase.getDao()
    }

    @Test
    fun testInsertOneItem() {
        val musicData = TestMusicUtil.createArtistsList(1)
        val subscriber = TestSubscriber<Int>()

        musicDao.insertMusic(musicData)
            .toTest()
            .subscribe(subscriber)

        subscriber.assertValues(1)
        subscriber.assertComplete()

        val selectSubscriber = TestSubscriber<Int>()

        musicDao.retrieveMusicData()
            .toTestData()
            .subscribe(selectSubscriber)

        subscriber.assertValues(1)
        subscriber.assertComplete()
    }

    @Test
    fun testInsertMultipleItems() {
        val data = TestMusicUtil.createArtistsList(60)
        val subscriber = TestSubscriber<Int>()

        musicDao.insertMusic(data)
            .toTest()
            .subscribe(subscriber)

        subscriber.assertValues(data.size)
        subscriber.assertComplete()

        val selectSubscriber = TestSubscriber<Int>()

        musicDao.retrieveMusicData()
            .toTestData()
            .subscribe(selectSubscriber)

        subscriber.assertValues(data.size)
        subscriber.assertComplete()
    }

    @Test
    fun testInsertOnConflict() {
        val data = TestMusicUtil.createArtistsList(5)

        musicDao.insertMusic(data)
            .toTest()
            .subscribe()

        val subscriber = TestSubscriber<Int>()
        val newData = listOf(TestMusicUtil.createById(1), TestMusicUtil.createById(4))
        musicDao.insertMusic(newData)
            .toTest()
            .subscribe(subscriber)

        val selectSubscriber = TestSubscriber<Int>()

        musicDao.retrieveMusicData()
            .toTestData()
            .subscribe(selectSubscriber)

        selectSubscriber.assertValues(5)
    }

    @Test
    fun testInsertOnConflictAndValid() {
        val data = TestMusicUtil.createArtistsList(5)

        musicDao.insertMusic(data)
            .toTest()
            .subscribe()

        val subscriber = TestSubscriber<Int>()
        val newData = listOf(TestMusicUtil.createById(1), TestMusicUtil.createById(6))
        musicDao.insertMusic(newData)
            .toTest()
            .subscribe(subscriber)

        val selectSubscriber = TestSubscriber<Int>()

        musicDao.retrieveMusicData()
            .toTestData()
            .subscribe(selectSubscriber)

        selectSubscriber.assertValues(6)
    }

    @After
    fun closeDB() {
        musicDataBase.close()
    }
}

private fun Single<List<Long>>.toTest() =
    subscribeOn(Schedulers.trampoline())
    .map { it.size }
    .toFlowable()

private fun Single<List<Results>>.toTestData() =
    subscribeOn(Schedulers.trampoline())
    .map { it.size }
    .toFlowable()

object TestMusicUtil {
    fun createArtistsList(size: Int) =
        (1..size).map {
            Results(
                Image("${it}00", "http://url${it}", "${it}00"),
                "${it}00",
                "name${it}",
                Currency("id${it}")
            )
        }

    fun createById(id: Int) =
        Results(
            Image("${id}00", "http://url${id}", "${id}00"),
            "${id}00",
            "name${id}",
            Currency("id${id}")
        )
}