package com.example.viewstatemvp.rxtest

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subscribers.TestSubscriber
import kotlinx.coroutines.yield
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit
import org.mockito.Mockito.*
import java.lang.Exception
import java.util.concurrent.TimeoutException

class RepositoryTimeoutTest {
    //Flowable.just("21 Savage", "Lil Peep", "DaBaby")
    lateinit var scheduler: TestScheduler
    lateinit var subscriber: TestSubscriber<String>

    @Before
    fun setUp() {
        scheduler = TestScheduler()
        subscriber = TestSubscriber()
    }

    @Test
    fun testNotRespondingRepository() {
        val repo = mockRapRepoTimeout(
            Flowable.never(),
            scheduler
        )
        repo.receiveRap().subscribe(subscriber)

        scheduler.advanceTimeBy(1990, TimeUnit.MILLISECONDS)
        subscriber.assertNoValues()
        subscriber.assertNoErrors()
        subscriber.assertNotComplete()

        scheduler.advanceTimeBy(20, TimeUnit.MILLISECONDS)
        subscriber.assertError { it is TimeoutException }
        subscriber.assertNoValues()
    }

    @Test
    fun testSuccessWithValidDelay() {
        val source = Flowable.timer(1500, TimeUnit.MILLISECONDS, scheduler)
            .flatMap { Flowable.just("21 Savage", "Lil Peep", "DaBaby") }

        val repo = mockRapRepoTimeout(source, scheduler)
        repo.receiveRap().subscribe(subscriber)

        scheduler.advanceTimeBy(1490, TimeUnit.MILLISECONDS)
        subscriber.assertNoValues()
        subscriber.assertNoErrors()
        subscriber.assertNotComplete()

        scheduler.advanceTimeTo(1501, TimeUnit.MILLISECONDS)
        subscriber.assertValueCount(3)
        subscriber.assertNoErrors()
        subscriber.assertComplete()
    }
}


fun mockRapRepoTimeout(
    mockResult: Flowable<String>,
    testScheduler: TestScheduler
): RapRepositoryTimeout {
    val repo = mock(RapRepository::class.java)
    `when`(repo.receiveRap()).thenReturn(mockResult)
    return RapRepositoryTimeout(repo, testScheduler)
}



interface RapRepository {
    fun receiveRap(): Flowable<String>
}

class RapRepositoryTimeout(
    private val rapRepository: RapRepository,
    private val scheduler: Scheduler
) : RapRepository {
    override fun receiveRap(): Flowable<String> {
        return rapRepository.receiveRap()
            .timeout(2, TimeUnit.SECONDS, scheduler)
    }
}