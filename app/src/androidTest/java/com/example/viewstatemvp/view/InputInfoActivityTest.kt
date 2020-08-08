package com.example.viewstatemvp.view

import androidx.test.espresso.Espresso
import org.junit.Before
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.assertion.ViewAssertions.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.viewstatemvp.view.test.InputInfoActivity

@RunWith(AndroidJUnit4::class)
@LargeTest
class InputInfoActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(InputInfoActivity::class.java)

    @Before
    fun setUp() {
    }

    @Test
    fun testInputs() {
        addGlobalAssertion("are all empty", matches(withText("")))
        //addGlobalAssertion("are all text typed", matches(withInputType(InputType.TYPE_CLASS_TEXT)))

        onView(withHint("username")).perform(actionWithAssertions(typeText("name")))
        onView(withHint("login")).perform(actionWithAssertions(typeText("login")))
        onView(withHint("password")).perform(actionWithAssertions(typeText("password"))
        //    , ViewActions.closeSoftKeyboard()
        )
        //onView(withId(R.id.submit_inputs)).perform(actionWithAssertions(click()))
        //onView(withId(R.id.result_tv)).check(matches(withText("Success")))
        clearGlobalAssertions()
    }

    fun testFailedInputs() {
        onView(withHint("username")).perform(typeText("name"))
        addGlobalAssertion("are all empty", matches(withText("")))
//        addGlobalAssertion("are all text typed", matches(withInputType(InputType.TYPE_CLASS_TEXT)))

        onView(withHint("username")).perform(actionWithAssertions(typeText("name")))
        //onView(withHint("login")).perform(actionWithAssertions(typeText("login")))
        onView(withHint("password")).perform(actionWithAssertions(typeText("password")))
        Espresso.closeSoftKeyboard()
//        onView(withId(R.id.submit_inputs)).perform(actionWithAssertions(click()))
//        onView(withId(R.id.result_tv)).check(matches(withText("Error")))
        clearGlobalAssertions()
    }

//    @After
//    fun tearDown() {
//        onView(withHint("username")).perform(typeText(""))
//        onView(withHint("login")).perform(typeText(""))
//        onView(withHint("password")).perform(typeText(""), ViewActions.closeSoftKeyboard())
//    }
}