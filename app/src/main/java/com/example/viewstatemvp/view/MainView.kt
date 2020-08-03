package com.example.viewstatemvp.view

import com.example.viewstatemvp.model.Results
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface MainView : MvpView {
    fun displayData(musicData: List<Results>)

    fun showProgress()

    fun hideProgress()

    fun showError()
}