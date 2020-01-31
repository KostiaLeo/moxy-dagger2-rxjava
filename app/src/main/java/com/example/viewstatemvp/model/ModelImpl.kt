package com.example.viewstatemvp.model

import javax.inject.Inject

class ModelImpl @Inject constructor() : Model {
    override fun loadData(): List<String> {
        return listOf("try", " to", " learn", " mvp", " and", " moxy")
    }
}