package com.example.viewstatemvp.view


import android.text.InputType
import android.widget.EditText
import android.widget.TextView
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
import org.hamcrest.CoreMatchers.*
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testRecyclerView() {
        val idlingResource = activityRule.activity.idlingResource
        IdlingRegistry.getInstance().register(idlingResource)

        onView(withId(R.id.recycler)).perform(actionOnItemAtPosition<MusicViewHolder>(0, click()))
        onView(withId(R.id.clicked_tv)).check(matches(withText(startsWith("Peep"))))

        onView(withId(R.id.recycler)).perform(
            actionOnItem<MusicViewHolder>(
                hasDescendant(withText(startsWith("Miyagi"))), click()
            )
        )
        onView(withId(R.id.clicked_tv)).check(matches(withText(startsWith("Mi"))))

        onView(withId(R.id.recycler))
            .perform(
                actionOnItem<MusicViewHolder>(
                    hasDescendant(
                        withText(containsString("ew"))
                    ), click()
                ).atPosition(2)
            )
        onView(withId(R.id.clicked_tv)).check(matches(withText(containsString("Sayonara Boy"))))
    
        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    @Before
    fun setUp() {
    }

    @Before
    fun tearDown() {
    }
}

fun holderWithId(id: Long): Matcher<MusicViewHolder> {
    return object : TypeSafeMatcher<MusicViewHolder>() {
        override fun describeTo(description: Description?) {
            description?.appendText("holder with item id: ")
        }

        override fun matchesSafely(item: MusicViewHolder?): Boolean {
            println("id: $id vs position: ${item?.adapterPosition?.toLong()}")
            return item?.adapterPosition?.toLong() == id
            //return item?.itemId == id
        }
    }
}