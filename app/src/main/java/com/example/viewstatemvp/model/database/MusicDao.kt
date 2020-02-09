package com.example.viewstatemvp.model.database

import androidx.room.*
import com.example.viewstatemvp.model.Results
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface MusicDao {
    @Query("SELECT * FROM music_table")
    fun retrieveMusicData(): Single<List<Results>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMusic(musicData: List<Results>): List<Long>
}

@Database(entities = [Results::class], version = 1, exportSchema = false)
abstract class MusicDataBase: RoomDatabase() {

    abstract fun getDao(): MusicDao
}