package com.example.viewstatemvp.activity_interaction

import androidx.test.espresso.Espresso
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
import com.example.viewstatemvp.view.test.FirstActivity
import org.hamcrest.CoreMatchers.*

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