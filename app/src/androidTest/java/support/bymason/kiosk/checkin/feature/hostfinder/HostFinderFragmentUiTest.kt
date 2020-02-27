package support.bymason.kiosk.checkin.feature.hostfinder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.findNavController
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import support.bymason.kiosk.checkin.HomeActivity
import support.bymason.kiosk.checkin.R
import support.bymason.kiosk.checkin.core.data.CheckInApi
import support.bymason.kiosk.checkin.core.model.Host
import support.bymason.kiosk.checkin.helpers.TestCoroutineDispatcherRule
import support.bymason.kiosk.checkin.helpers.UiTestBase
import support.bymason.kiosk.checkin.helpers.takeScreenshot

@RunWith(AndroidJUnit4::class)
class HostFinderFragmentUiTest : UiTestBase() {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val dispatcherRule = TestCoroutineDispatcherRule()

    private val mockApi = mock(CheckInApi::class.java)

    @Test
    fun diffEmptyHosts() {
        val scenario = launchActivity<HomeActivity>()

        scenario.onActivity { activity ->
            activity.supportFragmentManager.fragmentFactory = HomeActivity.Factory(
                    dispatchers = dispatcherRule.dispatchers,
                    api = mockApi
            )
            val controller = activity.findNavController(R.id.content)

            controller.navigate(R.id.host_finder, HostFinderFragmentArgs("").toBundle())
        }

        scenario.takeScreenshot()
    }

    @Test
    fun diffSampleHosts() {
        runBlocking {
            `when`(mockApi.findHosts(any())).thenReturn(listOf(
                    Host("a", "Person 1", null),
                    Host("b", "Person 2", null)
            ))
        }

        val scenario = launchActivity<HomeActivity>()

        scenario.onActivity { activity ->
            activity.supportFragmentManager.fragmentFactory = HomeActivity.Factory(
                    dispatchers = dispatcherRule.dispatchers,
                    api = mockApi
            )
            val controller = activity.findNavController(R.id.content)

            controller.navigate(R.id.host_finder, HostFinderFragmentArgs("").toBundle())
        }
        onView(withId(R.id.search)).perform(typeText("Foobar"))

        scenario.takeScreenshot()
    }
}
