package com.example.viewstatemvp

import androidx.test.espresso.Espresso
import org.junit.After
import org.junit.Before
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.viewstatemvp.R
import androidx.test.espresso.contrib.RecyclerViewActions.*
import com.example.viewstatemvp.model.Music
import com.example.viewstatemvp.model.Results
import com.example.viewstatemvp.view.test.FirstActivity
import org.hamcrest.CoreMatchers.*
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
@LargeTest
class ActivityResultBothUITest {

    @get:Rule
    val activityRule = ActivityTestRule(FirstActivity::class.java)

    @Test
    fun startSecondForResult_getOK() {
        onView(withText("name")).perform(click())
        onView(withId(R.id.input_data)).check(matches(withHint("input name")))
        onView(withId(R.id.error_tv)).check(matches(not(isDisplayed())))

        onView(withHint("input name")).perform(typeText("myName"))
        onView(withText("submit")).perform(click())

        onView(withId(R.id.info_tv)).check(matches(withText("received name: myName")))
    }

    @Test
    fun startSecondForResult_canceled() {
        onView(withText("login")).perform(click())
        onView(withId(R.id.input_data)).check(matches(withHint("input login")))
        onView(withId(R.id.error_tv)).check(matches(not(isDisplayed())))
        onView(withHint("input login")).perform(typeText("myName"), ViewActions.pressBack())
        Espresso.pressBackUnconditionally()

        onView(withId(R.id.info_tv)).check(matches(withText("Canceled")))
    }

    @Test
    fun startSecondForResult_emptyInput() {
        // invalid attempt
        onView(withText("password")).perform(click())
        onView(withId(R.id.input_data)).check(matches(withHint("input password")))

        onView(withText("submit")).perform(click())

        onView(withId(R.id.error_tv)).check(matches(allOf(withText("Empty input"), isDisplayed())))

        // trying perform valid attempt
        onView(withHint("input password")).perform(typeText("myPwd"))
        onView(withText("submit")).perform(click())

        onView(withId(R.id.info_tv)).check(matches(withText("received password: myPwd")))
    }
}