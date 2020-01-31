package com.example.viewstatemvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.viewstatemvp.model.ModelImpl
import com.example.viewstatemvp.view.MainView

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {
    private val model = ModelImpl()

    init {
        getData()
    }

    fun getData() {
        val data = model.loadData()
        println("data in presenter: $data")
        viewState.displayData(data)
    }
}