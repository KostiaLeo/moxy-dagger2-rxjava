package com.example.viewstatemvp.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.viewstatemvp.model.Results

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface MainView : MvpView {
    fun displayData(musicData: List<Results>)

    fun showProgress()

    fun hideProgress()

    fun showError()
}