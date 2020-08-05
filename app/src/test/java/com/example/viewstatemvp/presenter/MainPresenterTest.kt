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
import org.mockito.ArgumentMatchers.*
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

        verifyNoMoreInteractions(viewState)
    }

    @Test
    fun loadDataSuccesOrder() {
        `when`(repository.retrieveData()).thenReturn(Single.just(mockedResultList))
        `when`(repository.saveData(mockedResultList)).thenReturn(disposable)
        presenter.loadData()
        viewState.showProgress()
        viewState.hideProgress()
        viewState.displayData(mockedResultList)
        verify(viewState, never()).showError()

        val saveDescription = "app gotta save the data on successful fetching in order to use it in unexpected cases"
        verify(repository, description(saveDescription)).saveData(mockedResultList)

        val inOrder = inOrder(viewState)
        inOrder.verify(viewState).showProgress()
        inOrder.verify(viewState).hideProgress()
        inOrder.verify(viewState).displayData(anyList())

        val inOrderSeveral = inOrder(viewState, repository)
        inOrderSeveral.verify(viewState).showProgress()
        inOrderSeveral.verify(repository).saveData(anyList())
        inOrderSeveral.verify(viewState).hideProgress()
        inOrderSeveral.verify(viewState).displayData(anyList())
    }

    @Test
    fun loadEmptyData() {
        `when`(repository.retrieveData()).thenReturn(Single.just(emptyResultList), Single.just(mockedResultList))
        `when`(repository.connectivityObservable(context)).thenReturn(
            Observable.just(
                Connectivity.available(true).build()
            )
        )
        presenter.loadData()
        // verify(mock).someMethod();
        // verify(mock, times(10)).someMethod();
        // verify(mock, atLeastOnce()).someMethod();
        verify(repository).connectivityObservable(context)
        verify(viewState, times(2)).showProgress()
        verify(viewState, times(2)).hideProgress()
        verify(viewState, times(2)).showError()

        verify(repository, times(2)).retrieveData()
        verify(repository, atMost(1)).saveData(anyList())

    }

    @Test
    fun loadDataFailed() {

//        1) you can chain multiple return values which be returned in the mentioned order
//        2) thenThrow - as thenReturn, but throw Exception on the method calling
//        `when`(mockObject.add(any()))
//            .thenReturn(true, false)
//            .thenThrow(IllegalArgumentException())

        `when`(repository.retrieveData()).thenReturn(Single.error(IllegalStateException()))

//        it's possible to create and instruct mock object in 1 line
        val repo = `when`(mock(Repository::class.java).retrieveData())
                .thenReturn(Single.error(IllegalStateException()))
                .getMock<Repository>()

        // anyList(), anyInt(), any(AnyClass::class.java)
        // it's possible to use matchers in instructing mocks:
        // when(mockObject.addAll(anyList())).thenReturn(true);
        // when(mockObject.add(startsWith("elem"))).thenReturn(true);

        presenter.loadData()
        verify(viewState, never()).displayData(anyList())
        verify(viewState, never().description("app mustn't show any data if an error occurred"))
            .displayData(anyList())
        verify(repo, never()).saveData(anyList())
        verify(viewState).showProgress()
        verify(viewState).hideProgress()
        verify(viewState).showError()
    }
}