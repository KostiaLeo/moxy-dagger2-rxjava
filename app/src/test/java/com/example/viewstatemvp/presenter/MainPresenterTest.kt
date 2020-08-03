package com.example.viewstatemvp.presenter

import android.content.Context
import android.net.NetworkInfo
import com.example.viewstatemvp.model.Currency
import com.example.viewstatemvp.model.Image
import com.example.viewstatemvp.model.Repository
import com.example.viewstatemvp.model.Results
import com.example.viewstatemvp.view.`MainView$$State`
import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class MainPresenterTest {
    lateinit var presenter: MainPresenter

    @Mock
    lateinit var viewState: `MainView$$State`

    @Mock
    lateinit var repository: Repository

    @Mock
    lateinit var context: Context

    @Mock
    lateinit var disposable: Disposable

    private val mockedResultList =
        listOf(
            Results(Image("100", "http://url1", "100"), "100", "name1", Currency("id1")),
            Results(Image("200", "http://url2", "200"), "200", "name2", Currency("id2")),
            Results(Image("300", "http://url3", "300"), "300", "name3", Currency("id3"))
        )

    private val emptyResultList = emptyList<Results>()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter =
            MainPresenter(repository, context, Schedulers.trampoline(), Schedulers.trampoline())
        presenter.setViewState(viewState)
    }

    @After
    fun tearDown() {
        presenter.onDestroy()
    }

    @Test
    fun loadDataSuccess() {
        `when`(repository.retrieveData()).thenReturn(Single.just(mockedResultList))
        `when`(repository.saveData(mockedResultList)).thenReturn(disposable)
        presenter.loadData()
        verify(viewState).showProgress()
        verify(viewState).hideProgress()
        verify(viewState, never()).showError()
        verify(viewState).displayData(mockedResultList)
        verify(repository).saveData(mockedResultList)
    }

    @Test
    fun loadEmptyData() {
        `when`(repository.retrieveData()).thenReturn(Single.just(emptyResultList))
        `when`(repository.connectivityObservable(context)).thenReturn(
            Observable.just(
                Connectivity.state(
                    NetworkInfo.State.CONNECTED
                ).build()
            )
        )
        presenter.loadData()
        // verify(mock).someMethod();
        // verify(mock, times(10)).someMethod();
        // verify(mock, atLeastOnce()).someMethod();
        verify(repository).connectivityObservable(context)
        verify(viewState).showProgress()
        verify(viewState).hideProgress()
        verify(viewState).showError()
    }

    @Test
    fun loadDataFailed() {
        `when`(repository.retrieveData()).thenReturn(Single.error(IllegalStateException()))
        presenter.loadData()
        verify(viewState, never()).displayData(mockedResultList)
        verify(viewState, never()).displayData(emptyResultList)
        verify(repository, never()).saveData(ArgumentMatchers.anyList())
        verify(viewState).showProgress()
        verify(viewState).hideProgress()
        verify(viewState).showError()
    }
}