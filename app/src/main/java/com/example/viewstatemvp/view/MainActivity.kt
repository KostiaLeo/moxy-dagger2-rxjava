package com.example.viewstatemvp.view

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.viewstatemvp.R
import com.example.viewstatemvp.di.App
import com.example.viewstatemvp.presenter.MainPresenter
import javax.inject.Inject


class MainActivity : MvpAppCompatActivity(), MainView {
    @Inject
    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter(): MainPresenter = presenter
    
    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent?.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.getData()
    }

    override fun displayData(data: String) {
        println("data from view: $data")
    }
}
