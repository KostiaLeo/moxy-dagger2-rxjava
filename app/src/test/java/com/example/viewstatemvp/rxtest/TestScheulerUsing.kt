package com.example.viewstatemvp.rxtest

import io.reactivex.Flowable
import io.reactivex.Notification
import io.reactivex.exceptions.CompositeException
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subscribers.TestSubscriber
import org.junit.Test
import java.util.concurrent.TimeUnit

class TestScheulerUsing {
    val scheduler = TestScheduler()

    @Test
    fun testIntervalStreams() {
        val slow = Flowable.interval(50, TimeUnit.MILLISECONDS, scheduler).map { "Slow $it" }.take(2)
        val fast = Flowable.interval(20, TimeUnit.MILLISECONDS, scheduler).map { "Fast $it" }
        slow.mergeWith(fast)
        slow.concatWith(fast).subscribe(System.out::println)

        TimeUnit.SECONDS.sleep(1)
        println("1 second later")
        scheduler.advanceTimeBy(51, TimeUnit.MILLISECONDS)

        TimeUnit.SECONDS.sleep(1)
        println("2 seconds later")
        scheduler.advanceTimeBy(72, TimeUnit.MILLISECONDS)

        TimeUnit.SECONDS.sleep(1)
        println("3 seconds later")
        scheduler.advanceTimeBy(50, TimeUnit.MILLISECONDS)

    }

    @Test
    fun testSubscriberAssertions() {
        val subscriber = TestSubscriber<String>()

        Flowable.just("Offset" to "Migos", "Peep" to "GothBoiClique", "Takeoff" to "Migos", "Travis Scott" to "Jackboys", "Quavo" to "Migos")
            .concatMapDelayError {
                if (it.second == "Migos") {
                    Flowable.just(it.first)
                } else {
                    Flowable.error(NotMigosException())
                }
            }.subscribe(subscriber)

        subscriber.assertValueCount(3)
        subscriber.assertValues("Offset", "Takeoff", "Quavo")
        subscriber.assertError { error -> (error as CompositeException).exceptions.any { it is NotMigosException } }

    }
}