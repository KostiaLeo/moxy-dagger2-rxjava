package com.example.viewstatemvp

class Calculator {

    fun add(a: Int, b: Int) = a + b

    fun subtract(a: Int, b: Int) = a - b

    fun multiply(a: Int, b: Int) = a * b

    fun divide(a: Int, b: Int): Int {
        b.takeIf { it != 0 }?.let {
            return a / it
        }
        return 0
    }
}