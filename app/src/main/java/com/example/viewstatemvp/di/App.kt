package com.example.viewstatemvp.di

import android.app.Application

class App : Application() {
    companion object {
        val appComponent: AppComponent? = DaggerAppComponent.create()
    }
}