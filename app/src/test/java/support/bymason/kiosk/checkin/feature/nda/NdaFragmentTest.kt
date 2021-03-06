package support.bymason.kiosk.checkin.feature.nda

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.google.firebase.nongmsauth.FirebaseAuthCompat
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
import support.bymason.kiosk.checkin.databinding.NdaFragmentBinding
import support.bymason.kiosk.checkin.helpers.TestCoroutineDispatcherRule

@RunWith(AndroidJUnit4::class)
class NdaFragmentTest {
    @get:Rule
    val dispatcherRule = TestCoroutineDispatcherRule()

    private val mockAuth = mock(FirebaseAuthCompat::class.java)
    private val mockApi = mock(CheckInApi::class.java)

    @Test
    fun `NDA signing page loads on create`() {
        runBlocking {
            `when`(mockApi.generateNdaLink(any())).thenReturn("https://google.com")
        }

        val scenario = launchFragment()
        scenario.onFragment { fragment ->
            val binding = NdaFragmentBinding.bind(fragment.requireView())

            assertThat(binding.root.url).isEqualTo("https://google.com")
        }
    }

    private fun launchFragment(
            sessionId: String = "mySession"
    ) = launchFragmentInContainer<NdaFragment>(
            NdaFragmentArgs(sessionId).toBundle(),
            R.style.Theme_MaterialComponents_DayNight_DarkActionBar,
            HomeActivity.Factory(
                    dispatchers = dispatcherRule.dispatchers,
                    auth = mockAuth,
                    api = mockApi
            )
    )
}
