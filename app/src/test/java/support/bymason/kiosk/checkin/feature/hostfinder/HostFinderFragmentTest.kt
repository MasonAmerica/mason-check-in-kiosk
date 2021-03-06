package support.bymason.kiosk.checkin.feature.hostfinder

import androidx.core.view.isVisible
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
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
import support.bymason.kiosk.checkin.HomeActivity
import support.bymason.kiosk.checkin.R
import support.bymason.kiosk.checkin.core.data.CheckInApi
import support.bymason.kiosk.checkin.core.model.Host
import support.bymason.kiosk.checkin.databinding.HostFinderFragmentBinding
import support.bymason.kiosk.checkin.helpers.TestCoroutineDispatcherRule

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
        runBlocking {
            `when`(mockApi.findHosts(any())).thenReturn(listOf(
                    Host("id", "Mr Robot", null)))
        }

        val scenario = launchFragment()
        scenario.onFragment { fragment ->
            val binding = HostFinderFragmentBinding.bind(fragment.requireView())

            binding.search.setText("Person")
            InstrumentationRegistry.getInstrumentation().waitForIdleSync()
            Thread.sleep(500) // Wait for ListAdapter diffing

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
        runBlocking {
            `when`(mockApi.findHosts(any())).thenReturn(listOf(Host("id", "name", null)))
            `when`(mockApi.updateSessionForHereToSee(any(), any())).thenReturn("foobar")
        }

        val mockNavController = mock(NavController::class.java)
        val scenario = launchFragment("foobar")
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)

            val binding = HostFinderFragmentBinding.bind(fragment.requireView())
            binding.search.setText("Person")
        }

        Thread.sleep(500) // Wait for ListAdapter diffing
        onView(withId(R.id.hosts)).perform(actionOnItemAtPosition<HostViewHolder>(0, click()))

        verify(mockNavController).navigate(HostFinderFragmentDirections.next("foobar"))
    }

    private fun launchFragment(
            sessionId: String = "mySession"
    ) = launchFragmentInContainer<HostFinderFragment>(
            HostFinderFragmentArgs(sessionId).toBundle(),
            R.style.Theme_MaterialComponents_DayNight_DarkActionBar,
            HomeActivity.Factory(
                    dispatchers = dispatcherRule.dispatchers,
                    auth = mockAuth,
                    api = mockApi
            )
    )
}
