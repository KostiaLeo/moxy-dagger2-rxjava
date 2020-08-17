package com.example.viewstatemvp.presenter

import android.content.Context
import com.example.viewstatemvp.model.Currency
import com.example.viewstatemvp.model.Image
import com.example.viewstatemvp.model.Repository
import com.example.viewstatemvp.model.Results
import com.example.viewstatemvp.rules.LoggerRule
import com.example.viewstatemvp.view.`MainView$$State`
import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class MainPresenterTest {

    @get:Rule
    val rule = LoggerRule()

    private lateinit var presenter: MainPresenter

    @Mock
    lateinit var customViewState: `MainView$$State`

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
                .apply { setViewState(customViewState) }

        rule.log("setUp")
    }

    @After
    fun tearDown() {
        presenter.onDestroy()
        rule.log("tearDown")
    }

    @Test
    fun loadDataSuccess() {
        `when`(repository.retrieveData()).thenReturn(Single.just(mockedResultList))
        `when`(repository.saveData(mockedResultList)).thenReturn(disposable)
        presenter.loadData()
        verify(customViewState).showProgress()
        verify(customViewState).hideProgress()
        verify(customViewState, never()).showError()
        verify(customViewState).displayData(mockedResultList)
        verify(repository).saveData(mockedResultList)

        verifyNoMoreInteractions(customViewState)

        rule.log("loadDataSuccess")
    }

    @Test
    fun loadDataSuccesOrder() {
        `when`(repository.retrieveData()).thenReturn(Single.just(mockedResultList))
        `when`(repository.saveData(mockedResultList)).thenReturn(disposable)
        presenter.loadData()
        customViewState.showProgress()
        customViewState.hideProgress()
        customViewState.displayData(mockedResultList)
        verify(customViewState, never()).showError()

        val saveDescription =
            "app gotta save the data on successful fetching in order to use it in unexpected cases"
        verify(repository, description(saveDescription)).saveData(mockedResultList)

        val inOrder = inOrder(customViewState)
        inOrder.verify(customViewState).showProgress()
        inOrder.verify(customViewState).hideProgress()
        inOrder.verify(customViewState).displayData(anyList())

        val inOrderSeveral = inOrder(customViewState, repository)
        inOrderSeveral.verify(customViewState).showProgress()
        inOrderSeveral.verify(repository).saveData(anyList())
        inOrderSeveral.verify(customViewState).hideProgress()
        inOrderSeveral.verify(customViewState).displayData(anyList())
    }

    @Test
    fun loadEmptyData() {
        `when`(repository.retrieveData()).thenReturn(
            Single.just(emptyResultList),
            Single.just(mockedResultList)
        )
        `when`(repository.getConnectivityObservable(context)).thenReturn(
            Observable.just(
                Connectivity.available(true).build()
            )
        )
        presenter.loadData()
        // verify(mock).someMethod(); 
        // verify(mock, times(10)).someMethod();
        // verify(mock, atLeastOnce()).someMethod();
        verify(repository).getConnectivityObservable(context)
        verify(customViewState, times(2)).showProgress()
        verify(customViewState, times(2)).hideProgress()
        verify(customViewState, times(2)).showError()

        verify(repository, times(2)).retrieveData()
        verify(repository, atMost(1)).saveData(anyList())

    }

    @Test()
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
        verify(customViewState, never()).displayData(anyList())
        verify(
            customViewState,
            never().description("app mustn't show any data if an error occurred")
        )
            .displayData(anyList())
        verify(repo, never()).saveData(anyList())
        verify(customViewState).showProgress()
        verify(customViewState).hideProgress()
        verify(customViewState).showError()
    }
}