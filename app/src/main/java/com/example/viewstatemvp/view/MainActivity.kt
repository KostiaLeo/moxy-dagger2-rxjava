package com.example.viewstatemvp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.viewstatemvp.R
import com.example.viewstatemvp.di.DaggerApp
import com.example.viewstatemvp.presenter.MainPresenter
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainView {

    @Inject
    @InjectPresenter(type = PresenterType.GLOBAL)
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter(): MainPresenter = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerApp.allComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.display()
    }

    override fun showData(data: List<String>) {
        println("HELLOOOOOOOOOOOOOOO")
        println(data)
    }
}
