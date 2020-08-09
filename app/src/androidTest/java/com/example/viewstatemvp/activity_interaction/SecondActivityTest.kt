package com.example.viewstatemvp.activity_interaction

import android.content.Intent
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.assertion.ViewAssertions.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.viewstatemvp.R
import com.example.viewstatemvp.view.test.SecondActivity
import org.hamcrest.CoreMatchers.*
import org.junit.After
import org.junit.Before
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.text.SimpleDateFormat

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
class SecondActivityCustomRuleTest {
    @get:Rule
    val activityRule =
        SecondActivityTestRule()

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

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