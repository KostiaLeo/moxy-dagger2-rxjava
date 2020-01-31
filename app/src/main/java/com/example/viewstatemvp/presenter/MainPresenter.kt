package com.example.viewstatemvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.viewstatemvp.model.Model
import com.example.viewstatemvp.view.MainView
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
    private val model: Model
) : MvpPresenter<MainView>() {

//    @Inject
//    lateinit var model: Model
    private lateinit var data: String

    init {
        //App.appComponent.inject(this)
        loadData()
    }

    private fun loadData() {
        data = model.loadData()
        println("data in presenter: $data")
    }

    fun getData(){
        viewState.displayData(data)
    }
}