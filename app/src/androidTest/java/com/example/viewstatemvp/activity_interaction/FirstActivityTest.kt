package com.example.viewstatemvp.activity_interaction

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.intent.Intents.*
import androidx.test.espresso.intent.matcher.BundleMatchers.*
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.intent.matcher.UriMatchers.hasHost
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.rule.ActivityTestRule
import com.example.viewstatemvp.R
import com.example.viewstatemvp.view.test.FirstActivity
import kotlinx.android.synthetic.main.activity_first.*
import org.hamcrest.CoreMatchers.*
import org.junit.Rule

import org.junit.Test

@RunWith(AndroidJUnit4::class)
@LargeTest
class FirstActivityIntentRuleTest {

    @get:Rule
    val intentsRule = IntentsTestRule(FirstActivity::class.java)

    @Test
    fun checkResultIntent() {
        onView(withText("login")).perform(click())

        intended(
            hasExtras(
                hasEntry(equalTo("type"), equalTo("login"))
            )
        )

// More complex example:
//   here we check intent for specific action, category, data, extras and package
//
//        intended(allOf(
//            hasAction(equalTo(Intent.ACTION_VIEW)),
//            hasCategories(hasItem(equalTo(Intent.CATEGORY_BROWSABLE))),
//            hasData(hasHost(equalTo("www.google.com"))),
//            hasExtras(allOf(
//                hasEntry(equalTo("key1"), equalTo("value1")),
//                hasEntry(equalTo("key2"), equalTo("value2")))),
//            toPackage("com.android.browser")))
    }

    @Test
    fun testSuccessfulResponseFromSecondActivity() {
        val intent = Intent().putExtra("response", "response name")
        val activityResult = Instrumentation.ActivityResult(Activity.RESULT_OK, intent)

        intending(
            hasExtras(
                allOf(
                    hasKey(equalTo("type")),
                    hasValue(equalTo("name"))
                )
            )
        ).respondWith(activityResult)

        onView(withText("name")).perform(click())
        intended(
            hasExtras(
                hasEntry(equalTo("type"), equalTo("name"))
            )
        )

        onView(withId(R.id.info_tv)).check(matches(withText("response name")))
    }

    @Test
    fun testCanceledResponseFromSecondActivity() {
        val activityResult = Instrumentation.ActivityResult(Activity.RESULT_CANCELED, null)

        intending(
            hasExtras(
                allOf(
                    hasKey(equalTo("type")),
                    hasValue(any(String::class.java))
                )
            )
        ).respondWith(activityResult)

        onView(withText("password")).perform(click())

        intended(
            hasExtras(
                hasKey(equalTo("type"))
            )
        )

        onView(withId(R.id.info_tv)).check(matches(withText("Canceled")))
    }
}