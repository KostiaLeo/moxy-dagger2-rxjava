package com.example.viewstatemvp.view

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.viewstatemvp.R
import com.example.viewstatemvp.presenter.MainPresenter


class MainActivity : MvpAppCompatActivity(), MainView {

    @InjectPresenter
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.getData()
    }

    override fun displayData(data: String) {
        println("data from view: $data")
    }
}
