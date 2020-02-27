package support.bymason.kiosk.checkin.feature.identity

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.findNavController
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import support.bymason.kiosk.checkin.HomeActivity
import support.bymason.kiosk.checkin.R
import support.bymason.kiosk.checkin.core.data.CheckInApi
import support.bymason.kiosk.checkin.core.model.Company
import support.bymason.kiosk.checkin.core.model.GuestField
import support.bymason.kiosk.checkin.core.model.GuestFieldType
import support.bymason.kiosk.checkin.helpers.TestCoroutineDispatcherRule
import support.bymason.kiosk.checkin.helpers.UiTestBase
import support.bymason.kiosk.checkin.helpers.takeScreenshot

@RunWith(AndroidJUnit4::class)
class IdentityFragmentUiTest : UiTestBase() {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val dispatcherRule = TestCoroutineDispatcherRule()

    private val mockApi = mock(CheckInApi::class.java)

    @Test
    fun diffEmptyForm() {
        runBlocking {
            `when`(mockApi.getCompanyMetadata()).thenReturn(Company(
                    "Mason", "http://google.com/404"))
            `when`(mockApi.getGuestFields()).thenReturn(listOf(
                    GuestField("id1", GuestFieldType.NAME, "Full name", true, ".+"),
                    GuestField("id2", GuestFieldType.EMAIL, "Email address", true, ".+")
            ))
        }

        val scenario = launchActivity<HomeActivity>()

        scenario.onActivity { activity ->
            activity.supportFragmentManager.fragmentFactory = HomeActivity.Factory(
                    dispatchers = dispatcherRule.dispatchers,
                    api = mockApi
            )
            val controller = activity.findNavController(R.id.content)

            controller.navigate(R.id.identity)
        }

        scenario.takeScreenshot()
    }
}
