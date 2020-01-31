package com.example.viewstatemvp.model

import javax.inject.Inject

class ModelImpl @Inject constructor() : Model {

    override fun loadData(): String {
        return "Data, retrieved by mvp viewState"
    }
}