package com.example.viewstatemvp.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.viewstatemvp.R
import com.example.viewstatemvp.databinding.ActivityMainBinding
import com.example.viewstatemvp.di.App
import com.example.viewstatemvp.model.Results
import com.example.viewstatemvp.presenter.MainPresenter
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), MainView {

    @Inject
    @InjectPresenter
    lateinit var presenter: MainPresenter

    private lateinit var binding: ActivityMainBinding

    @ProvidePresenter
    fun providePresenter(): MainPresenter = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this as AppCompatActivity, R.layout.activity_main)
        binding.recycler.adapter = MusicAdapter()

        presenter.loadData()
    }

    override fun displayData(musicData: List<Results>) {
        binding.dataList = musicData
        binding.executePendingBindings()
    }

    override fun showProgress() {
        binding.progressRunning = View.VISIBLE
    }

    override fun hideProgress() {
        binding.progressRunning = View.GONE
    }

    override fun showError() {
        hideProgress()
        Snackbar.make(this.binding.root, "Check internet connection, failed loading", Snackbar.LENGTH_LONG).show()
    }
}
