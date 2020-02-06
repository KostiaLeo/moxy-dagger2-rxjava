package com.example.viewstatemvp.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.viewstatemvp.model.network.Music
import com.example.viewstatemvp.model.network.Results

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface MainView : MvpView {
    fun displayData(musicData: List<Results>)
}