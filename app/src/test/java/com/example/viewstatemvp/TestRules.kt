package com.example.viewstatemvp

import android.util.Log
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class CustomRule : TestRule {
    override fun apply(base: Statement?, description: Description?): Statement {
        println("CustomRule --> class: ${description?.testClass?.simpleName} with method: ${description?.methodName}")
        return object : Statement() {
            override fun evaluate() {
                println("CustomRule --> before launch evaluate")
                base?.evaluate()
                println("CustomRule --> after launch evaluate")
            }
        }
    }
}