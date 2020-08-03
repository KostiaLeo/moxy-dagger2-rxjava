package com.example.viewstatemvp.view

import com.example.viewstatemvp.model.Results
import moxy.MvpAppCompatFragment

class MainFragment : MvpAppCompatFragment(), MainView {

    override fun displayData(musicData: List<Results>) {
    }

    override fun showProgress() {
    }

    override fun hideProgress() {
    }

    override fun showError() {
    }
}