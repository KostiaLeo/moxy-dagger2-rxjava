package com.example.viewstatemvp.di

import android.app.Application

class DaggerApp : Application() {

    companion object {
        val allComponent: AppComponent = DaggerAppComponent.create()
    }
}