package com.example.viewstatemvp

import android.content.Intent
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
import com.example.viewstatemvp.view.test.SecondActivity
import org.hamcrest.CoreMatchers.*
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
@LargeTest
class SecondActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(SecondActivity::class.java, false, false)

    @Test
    fun showIntentData() {
        val intent = Intent().putExtra("type", "name")
        activityRule.launchActivity(intent)

        onView(withId(R.id.input_data)).check(matches(withHint("input name")))
        onView(withId(R.id.error_tv)).check(matches(not(isDisplayed())))
    }
}

@RunWith(AndroidJUnit4::class)
@LargeTest
class SecondActivityCustomRulTest {
    @get:Rule
    val activityRule = SecondActivityTestRule()

    @Test
    fun testWithCustomRule() {
        onView(withId(R.id.input_data)).check(matches(withHint("input login")))
        onView(withId(R.id.error_tv)).check(matches(not(isDisplayed())))
    }
}

class SecondActivityTestRule : ActivityTestRule<SecondActivity>(SecondActivity::class.java) {
    override fun getActivityIntent(): Intent {
        return Intent().putExtra("type", "login")
    }
}