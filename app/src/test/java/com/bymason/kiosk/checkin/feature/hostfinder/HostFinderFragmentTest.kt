package com.bymason.kiosk.checkin.feature.hostfinder

import androidx.core.view.isVisible
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.bymason.kiosk.checkin.CheckInNavHostFragment
import com.bymason.kiosk.checkin.R
import com.bymason.kiosk.checkin.core.data.CheckInApi
import com.bymason.kiosk.checkin.core.model.Host
import com.bymason.kiosk.checkin.databinding.HostFinderFragmentBinding
import com.bymason.kiosk.checkin.helpers.TestCoroutineDispatcherRule
import com.google.common.truth.Truth.assertThat
import com.google.firebase.nongmsauth.FirebaseAuthCompat
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class HostFinderFragmentTest {
    @get:Rule
    val dispatcherRule = TestCoroutineDispatcherRule()

    private val mockAuth = mock(FirebaseAuthCompat::class.java)
    private val mockApi = mock(CheckInApi::class.java)

    @Test
    fun `No hosts found hint is visible on launch`() {
        val scenario = launchFragment()
        scenario.onFragment { fragment ->
            val binding = HostFinderFragmentBinding.bind(fragment.requireView())

            assertThat(binding.noHostsHint.isVisible).isTrue()
        }
    }

    @Test
    fun `Searching for host displays results`() {
        val scenario = launchFragment()
        scenario.onFragment { fragment ->
            val binding = HostFinderFragmentBinding.bind(fragment.requireView())
            runBlocking {
                `when`(mockApi.findHosts(any())).thenReturn(listOf(
                        Host("id", "Mr Robot", null)))
            }

            binding.search.setText("Person")
            InstrumentationRegistry.getInstrumentation().waitForIdleSync()

            onView(withId(R.id.hosts)).check(matches(hasDescendant(withText("Mr Robot"))))
        }
    }

    @Test
    fun `No hosts found hint is hidden with host results`() {
        val scenario = launchFragment()
        scenario.onFragment { fragment ->
            val binding = HostFinderFragmentBinding.bind(fragment.requireView())
            runBlocking {
                `when`(mockApi.findHosts(any())).thenReturn(listOf(
                        Host("id", "name", null)))
            }

            binding.search.setText("Person")

            assertThat(binding.noHostsHint.isVisible).isFalse()
        }
    }

    @Test
    fun `No hosts found hint is visible with empty host results`() {
        val scenario = launchFragment()
        scenario.onFragment { fragment ->
            val binding = HostFinderFragmentBinding.bind(fragment.requireView())
            runBlocking {
                `when`(mockApi.findHosts(any())).thenReturn(emptyList())
            }

            binding.search.setText("Person")

            assertThat(binding.noHostsHint.isVisible).isTrue()
        }
    }

    @Test
    fun `Selecting host result navigates to next destination`() {
        val host = Host("id", "name", null)
        val mockNavController = mock(NavController::class.java)
        val scenario = launchFragment("foobar")
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)

            val binding = HostFinderFragmentBinding.bind(fragment.requireView())
            runBlocking {
                `when`(mockApi.findHosts(any())).thenReturn(listOf(host))
                `when`(mockApi.updateSession(any(), any(), any())).thenReturn("foobar")
            }
            binding.search.setText("Person")
        }

        onView(withId(R.id.name)).perform(click())

        verify(mockNavController).navigate(HostFinderFragmentDirections.next("foobar"))
    }

    private fun launchFragment(
            sessionId: String = "mySession"
    ) = launchFragmentInContainer<HostFinderFragment>(
            HostFinderFragmentArgs(sessionId).toBundle(),
            R.style.Theme_MaterialComponents_DayNight_DarkActionBar,
            CheckInNavHostFragment.Factory(
                    dispatchers = dispatcherRule.dispatchers,
                    auth = mockAuth,
                    api = mockApi
            )
    )
}
