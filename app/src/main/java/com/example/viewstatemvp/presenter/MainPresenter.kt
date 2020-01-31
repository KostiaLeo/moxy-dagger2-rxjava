package com.example.viewstatemvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.viewstatemvp.model.Model
import com.example.viewstatemvp.view.MainView
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(private val model: Model) : MvpPresenter<MainView>(){

    fun display(): List<String>{
        val data = model.loadData()
        println(data.size)
        viewState.showData(data)
        return data
    }
}