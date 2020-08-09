package com.example.viewstatemvp.rules

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.text.SimpleDateFormat

class LoggerRule : TestRule {
    override fun apply(base: Statement?, description: Description?): Statement {
        return LoggerStatement(base, description)
    }

    fun log(message: String) {
        val text = buildString {
            append("[")
            append(SimpleDateFormat("dd/MM/YYYY").format(System.currentTimeMillis()))
            append("] --> ")
            append(message)
        }

        println(text)
    }

    class LoggerStatement(private val base: Statement?, private val description: Description?) :
        Statement() {
        override fun evaluate() {
            println("pre evaluation in class ${description?.testClass?.simpleName} with method ${description?.methodName}")
            base?.evaluate()
            println("post evaluation in class ${description?.testClass?.simpleName} with method ${description?.methodName}")
        }
    }

}