package com.bymason.kiosk.checkin

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class WelcomeFragmentTest {
    @Test
    fun `Clicking anywhere moves to the next screen`() {
        val mockNavController = mock(NavController::class.java)
        val scenario = launchFragmentInContainer<WelcomeFragment>()
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }

        onView(withId(R.id.root)).perform(click())

        verify(mockNavController).navigate(WelcomeFragmentDirections.next())
    }
}
