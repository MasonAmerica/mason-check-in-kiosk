package com.bymason.kiosk.checkin.feature.identity

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bymason.kiosk.checkin.R
import com.bymason.kiosk.checkin.core.model.Guest
import com.bymason.kiosk.checkin.databinding.IdentityFragmentBinding
import com.google.common.truth.Truth.assertThat
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class IdentityFragmentTest {
    @Ignore("One glorious summer day, the gods will smile down from the heavens and bestow " +
                    "upon us mortals a nice keyboard API.")
    @Test
    fun `Keyboard is visible on launch`() = Unit

    @Test
    fun `Input fields don't have errors on launch`() {
        val scenario = launchFragmentInContainer<IdentityFragment>(
                themeResId = R.style.Theme_MaterialComponents_DayNight_DarkActionBar)
        scenario.onFragment { fragment ->
            val binding = IdentityFragmentBinding.bind(fragment.requireView())

            assertThat(binding.nameLayout.error).isNull()
            assertThat(binding.emailLayout.error).isNull()
        }
    }

    @Test
    fun `Continue button is disabled on launch`() {
        val scenario = launchFragmentInContainer<IdentityFragment>(
                themeResId = R.style.Theme_MaterialComponents_DayNight_DarkActionBar)
        scenario.onFragment { fragment ->
            val binding = IdentityFragmentBinding.bind(fragment.requireView())

            assertThat(binding.next.isEnabled).isFalse()
        }
    }

    @Test
    fun `Moving to new field while current one is invalid shows error`() {
        val scenario = launchFragmentInContainer<IdentityFragment>(
                themeResId = R.style.Theme_MaterialComponents_DayNight_DarkActionBar)
        scenario.onFragment { fragment ->
            val binding = IdentityFragmentBinding.bind(fragment.requireView())

            binding.emailLayout.requestFocus()

            assertThat(binding.nameLayout.error).isNotNull()
            assertThat(binding.emailLayout.error).isNull()
        }
    }

    @Test
    fun `Going back to invalid field clears error`() {
        val scenario = launchFragmentInContainer<IdentityFragment>(
                themeResId = R.style.Theme_MaterialComponents_DayNight_DarkActionBar)
        scenario.onFragment { fragment ->
            val binding = IdentityFragmentBinding.bind(fragment.requireView())

            binding.emailLayout.requestFocus()
            binding.nameLayout.requestFocus()

            assertThat(binding.nameLayout.error).isNull()
            assertThat(binding.emailLayout.error).isNotNull()
        }
    }

    @Test
    fun `Continue button becomes enabled when all fields are valid`() {
        val scenario = launchFragmentInContainer<IdentityFragment>(
                themeResId = R.style.Theme_MaterialComponents_DayNight_DarkActionBar)
        scenario.onFragment { fragment ->
            val binding = IdentityFragmentBinding.bind(fragment.requireView())

            binding.name.setText("Name")
            binding.email.setText("name@exmaple.com")

            assertThat(binding.next.isEnabled).isTrue()
        }
    }

    @Ignore("The gods still hate us.")
    @Test
    fun `IME done action navigates to next destination`() = Unit

    @Test
    fun `Continue button navigates to next destination`() {
        val mockNavController = mock(NavController::class.java)
        val scenario = launchFragmentInContainer<IdentityFragment>(
                themeResId = R.style.Theme_MaterialComponents_DayNight_DarkActionBar)
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }
        onView(withId(R.id.name)).perform(typeText("My Name"))
        onView(withId(R.id.email)).perform(typeText("me@example.com"))

        onView(withId(R.id.next)).perform(click())

        verify(mockNavController).navigate(IdentityFragmentDirections.next(Guest(
                "My Name", "me@example.com")))
    }
}
