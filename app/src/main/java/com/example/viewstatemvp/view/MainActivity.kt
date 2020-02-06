package com.example.viewstatemvp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.viewstatemvp.R
import com.example.viewstatemvp.databinding.ActivityMainBinding
import com.example.viewstatemvp.di.App
import com.example.viewstatemvp.model.network.Music
import com.example.viewstatemvp.model.network.Results
import com.example.viewstatemvp.presenter.MainPresenter
import javax.inject.Inject


class MainActivity : MvpAppCompatActivity(), MainView {

    @Inject
    @InjectPresenter
    lateinit var presenter: MainPresenter

    private lateinit var binding: ActivityMainBinding

    @ProvidePresenter
    fun providePresenter(): MainPresenter = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent?.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this as AppCompatActivity, R.layout.activity_main)
        binding.recycler.adapter = MusicAdapter()

        presenter.loadData()
    }

    override fun displayData(musicData: List<Results>) {
        binding.dataList = musicData
        binding.executePendingBindings()
    }
}
