package com.example.viewstatemvp

import com.example.viewstatemvp.MigosMatcher.Companion.isMigos
import com.example.viewstatemvp.presenter.MainPresenter
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.hamcrest.CoreMatchers.*
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import kotlin.properties.Delegates

class CalculatorTest {
    var calculator by Delegates.notNull<Calculator>()

    @Before
    fun setUp() {
        calculator = Calculator()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun rapTest() {
        val s1 = String("Migos".toCharArray())
        val s2 = String("Migos".toCharArray())
        assertThat(s1, instanceOf(String::class.java))
        assertThat(s1, equalTo(s2))
        assertThat(s1, notNullValue())
        assertThat(s1, startsWith("M"))
        assertThat(s1, endsWith("s"))
        assertThat(s1, containsString("igo"))

        val rap = listOf("Quavo", "Takeoff", "Offset", "Travis Scott", "Drake")

        assertThat(rap, hasItem("Takeoff"))
        assertThat(rap, hasItems(containsString("off"), startsWith("T")))
        assertThat("Travis Scott", either(isMigos()).or(startsWith("Travis")))
    }

    @Test
    fun add() {
        assertEquals(8, calculator.add(-1, 9))
    }

    @Test
    fun subtract() {
        assertEquals(0, calculator.subtract(11, 11))
    }

    @Test
    fun multiply() {
        assertEquals(0, calculator.multiply(0, 15))
        assertEquals(15, calculator.multiply(3, 5))
    }

    @Test
    fun divide() {
        assertEquals(0, calculator.divide(5, 0))
        assertEquals(1, calculator.divide(5, 3))
    }
}

class MigosMatcher : TypeSafeMatcher<String>() {

    companion object {
        fun isMigos() = MigosMatcher()
    }

    private val migos = listOf("Quavo", "Takeoff", "Offset")

    override fun describeTo(description: Description?) {
        description?.appendText("The rapper should be a part of Migos (Quavo, Takeoff, Offset)")
    }

    override fun matchesSafely(item: String?): Boolean {
        item ?: return false
        return item in migos
    }
}