package com.nur.myapplication

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import com.nur.myapplication.ui.activity.MainActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class BlankFragmentTest {

    @Test
    fun testSnackbar() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.btn_snackbar)).perform(click())

        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText("OK")))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testChangeText() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.btn_change_text)).perform(click())
        onView(withId(R.id.tv_text)).check(matches(withText("Hello")))
    }

    @Test
    fun testNavigationToHome() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)

        onView(withId(R.id.btn_navigation)).perform(click())

        onView(withId(R.id.homeFragment))
            .check(matches(isDisplayed()))
    }
}