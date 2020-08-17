package com.example.viewstatemvp.rxtest

import io.reactivex.Flowable
import io.reactivex.Notification
import io.reactivex.Observable
import io.reactivex.Single
import org.intellij.lang.annotations.Flow
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Test
import java.lang.RuntimeException

class BlockingStreams {

    @Test
    fun concatMapTest() {
        val rapStream = Observable.fromArray("21 Savage", "Offset", "Blueface baby yeah aight", "Tyga")
                .concatMap { Observable.just(it + it[0]) }
                .toList()
                .blockingGet()

        assertTrue(rapStream.containsAll(listOf("21 Savage2", "OffsetO", "Blueface baby yeah aightB", "TygaT")))
    }

    @Test
    fun testOnError() {
        val array = arrayOf(0, 1)
        val single = Single.fromCallable { array[2] }

        try {
            single.blockingGet()
        } catch (e: RuntimeException) {
            assertTrue(e is ArrayIndexOutOfBoundsException)
        }
    }

    @Test
    fun testTypedStreamItems() {
        val streamItems = Flowable.just("Offset" to "Migos", "Peep" to "GothBoiClique", "Takeoff" to "Migos", "Travis Scott" to "Jackboys", "Quavo" to "Migos")
            .concatMapDelayError {
                if (it.second == "Migos") {
                    Flowable.just(it.first)
                } else {
                    Flowable.error(NotMigosException())
                }
            }.materialize().map{ it.isOnNext }.toList().blockingGet()

        assertTrue(streamItems == listOf(true, true, true, false))
    }
}

class NotMigosException : Exception() {
    override val message: String?
        get() = "Rapper is not part of Migos"
}