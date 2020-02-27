package support.bymason.kiosk.checkin.feature.nda

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.findNavController
import androidx.test.core.app.launchActivity
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
import support.bymason.kiosk.checkin.helpers.TestCoroutineDispatcherRule
import support.bymason.kiosk.checkin.helpers.UiTestBase
import support.bymason.kiosk.checkin.helpers.takeScreenshot

@RunWith(AndroidJUnit4::class)
class NdaFragmentUiTest : UiTestBase() {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val dispatcherRule = TestCoroutineDispatcherRule()

    private val mockApi = mock(CheckInApi::class.java)

    @Test
    fun diffEmptyNda() {
        runBlocking {
            `when`(mockApi.generateNdaLink(any())).thenReturn("")
        }

        val scenario = launchActivity<HomeActivity>()

        scenario.onActivity { activity ->
            activity.supportFragmentManager.fragmentFactory = HomeActivity.Factory(
                    dispatchers = dispatcherRule.dispatchers,
                    api = mockApi
            )
            val controller = activity.findNavController(R.id.content)

            controller.navigate(R.id.nda, NdaFragmentArgs("").toBundle())
        }

        scenario.takeScreenshot()
    }
}
