package com.example.viewstatemvp.view


import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.assertion.ViewAssertions.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.viewstatemvp.R
import com.example.viewstatemvp.view.test.EspressoActivity
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not

@RunWith(AndroidJUnit4::class)
@LargeTest
class EspressoActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(EspressoActivity::class.java)

    @Test
    fun textOnOneClick() {
        onView(withId(R.id.click_test_btn)).perform(click())

        onView(withId(R.id.tv_display)).check(matches(withText(
            R.string.travis
        )))
    }

    @Test
    fun textOnTwoClicks() {
        onView(withId(R.id.click_test_btn)).perform(click(), click())

        onView(withId(R.id.tv_display)).check(matches(withText(
            R.string.for_real
        )))
    }

    @Test
    fun inputTest() {
        onView(withHint("input")).perform(typeText("Miyagi is master"), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.submit)).perform(click())

        onView(withId(R.id.output_text))
            .check(
                matches(allOf(
                    withText("Output:Miyagi is master"),
                    isDisplayed(),
                    not(withText(""))
                ))
            )
    }

    @Test
    fun emptyInputTest() {

        onView(withHint("input")).perform(typeText(""))
        onView(withId(R.id.submit)).perform(click())
        onView(withId(R.id.output_text)).check(matches(withText("type smth")))
    }
}