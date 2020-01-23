package support.bymason.kiosk.checkin.feature.identity

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.android.material.textfield.TextInputLayout
import com.google.common.truth.Truth.assertThat
import com.google.firebase.nongmsauth.FirebaseAuthCompat
import kotlinx.coroutines.runBlocking
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.robolectric.Robolectric
import support.bymason.kiosk.checkin.HomeActivity
import support.bymason.kiosk.checkin.R
import support.bymason.kiosk.checkin.core.data.CheckInApi
import support.bymason.kiosk.checkin.core.model.Company
import support.bymason.kiosk.checkin.core.model.GuestField
import support.bymason.kiosk.checkin.core.model.GuestFieldType
import support.bymason.kiosk.checkin.databinding.IdentityFragmentBinding
import support.bymason.kiosk.checkin.helpers.TestCoroutineDispatcherRule

@RunWith(AndroidJUnit4::class)
class IdentityFragmentTest {
    @get:Rule
    val dispatcherRule = TestCoroutineDispatcherRule()

    private val mockAuth = mock(FirebaseAuthCompat::class.java)
    private val mockApi = mock(CheckInApi::class.java)

    @Ignore("One glorious summer day, the gods will smile down from the heavens and bestow " +
                    "upon us mortals a nice keyboard API.")
    @Test
    fun `Keyboard is visible on launch`() = Unit

    @Test
    fun `Config change succeeds`() {
        runBlocking {
            `when`(mockApi.getCompanyMetadata()).thenReturn(Company("Mason", "url"))
            `when`(mockApi.getGuestFields()).thenReturn(emptyList())
        }

        val scenario = launchFragment()
        scenario.recreate()
    }

    @Test
    fun `Input fields don't have errors on launch`() {
        runBlocking {
            `when`(mockApi.getCompanyMetadata()).thenReturn(Company("Mason", "url"))
            `when`(mockApi.getGuestFields()).thenReturn(listOf(
                    GuestField("id1", GuestFieldType.NAME, "foo", true, ".+"),
                    GuestField("id2", GuestFieldType.NAME, "foo", true, ".+")
            ))
        }

        val scenario = launchFragment()
        scenario.onFragment { fragment ->
            val binding = IdentityFragmentBinding.bind(fragment.requireView())
            val inputLayout1 = binding.fields.layoutManager!!.getChildAt(0) as TextInputLayout
            val inputLayout2 = binding.fields.layoutManager!!.getChildAt(1) as TextInputLayout

            assertThat(inputLayout1.error).isNull()
            assertThat(inputLayout2.error).isNull()
        }
    }

    @Test
    fun `Continue button is disabled on launch`() {
        runBlocking {
            `when`(mockApi.getCompanyMetadata()).thenReturn(Company("Mason", "url"))
            `when`(mockApi.getGuestFields()).thenReturn(listOf(
                    GuestField("id1", GuestFieldType.NAME, "foo", true, ".+"),
                    GuestField("id2", GuestFieldType.NAME, "foo", true, ".+")
            ))
        }

        val scenario = launchFragment()
        scenario.onFragment { fragment ->
            val binding = IdentityFragmentBinding.bind(fragment.requireView())

            assertThat(binding.next.isEnabled).isFalse()
        }
    }

    @Ignore("https://github.com/robolectric/robolectric/issues/5407")
    @Test
    fun `Moving to new field while current one is invalid shows error`() {
        runBlocking {
            `when`(mockApi.getCompanyMetadata()).thenReturn(Company("Mason", "url"))
            `when`(mockApi.getGuestFields()).thenReturn(listOf(
                    GuestField("id1", GuestFieldType.NAME, "foo", true, ".+"),
                    GuestField("id2", GuestFieldType.NAME, "foo", true, ".+")
            ))
        }

        val scenario = launchFragment()
        scenario.onFragment { fragment ->
            val binding = IdentityFragmentBinding.bind(fragment.requireView())
            val inputLayout1 = binding.fields.layoutManager!!.getChildAt(0) as TextInputLayout
            val inputLayout2 = binding.fields.layoutManager!!.getChildAt(1) as TextInputLayout

            inputLayout2.requestFocus()
            InstrumentationRegistry.getInstrumentation().waitForIdleSync()

            assertThat(inputLayout1.error).isNotNull()
            assertThat(inputLayout2.error).isNull()
        }
    }

    @Ignore("https://github.com/robolectric/robolectric/issues/5407")
    @Test
    fun `Going back to invalid field clears error`() {
        runBlocking {
            `when`(mockApi.getCompanyMetadata()).thenReturn(Company("Mason", "url"))
            `when`(mockApi.getGuestFields()).thenReturn(listOf(
                    GuestField("id1", GuestFieldType.NAME, "foo", true, ".+"),
                    GuestField("id2", GuestFieldType.NAME, "foo", true, ".+")
            ))
        }

        val scenario = launchFragment()
        scenario.onFragment { fragment ->
            val binding = IdentityFragmentBinding.bind(fragment.requireView())
            val inputLayout1 = binding.fields.layoutManager!!.getChildAt(0) as TextInputLayout
            val inputLayout2 = binding.fields.layoutManager!!.getChildAt(1) as TextInputLayout

            inputLayout2.requestFocus()
            inputLayout1.requestFocus()
            InstrumentationRegistry.getInstrumentation().waitForIdleSync()

            assertThat(inputLayout1.error).isNull()
            assertThat(inputLayout2.error).isNotNull()
        }
    }

    @Test
    fun `Continue button becomes enabled when all fields are valid`() {
        runBlocking {
            `when`(mockApi.getCompanyMetadata()).thenReturn(Company("Mason", "url"))
            `when`(mockApi.getGuestFields()).thenReturn(listOf(
                    GuestField("id1", GuestFieldType.NAME, "foo", true, ".+"),
                    GuestField("id2", GuestFieldType.NAME, "foo", true, ".+")
            ))
        }

        val scenario = launchFragment()
        scenario.onFragment { fragment ->
            val binding = IdentityFragmentBinding.bind(fragment.requireView())
            val inputLayout1 = binding.fields.layoutManager!!.getChildAt(0) as TextInputLayout
            val inputLayout2 = binding.fields.layoutManager!!.getChildAt(1) as TextInputLayout

            inputLayout1.editText!!.setText("A")
            inputLayout2.editText!!.setText("B")
            InstrumentationRegistry.getInstrumentation().waitForIdleSync()

            assertThat(binding.next.isEnabled).isTrue()
        }
    }

    @Ignore("The gods still hate us.")
    @Test
    fun `IME done action navigates to next destination`() = Unit

    @Test
    fun `Continue button navigates to next destination`() {
        runBlocking {
            `when`(mockApi.getCompanyMetadata()).thenReturn(Company("Mason", "url"))
            `when`(mockApi.getGuestFields()).thenReturn(listOf(
                    GuestField("id", GuestFieldType.NAME, "foo", false, null)))
            `when`(mockApi.updateSessionForCreate(any())).thenReturn("foobar")
        }

        val mockNavController = mock(NavController::class.java)
        val scenario = launchFragment()
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }

        onView(withId(R.id.next)).perform(click())

        verify(mockNavController).navigate(IdentityFragmentDirections.next("foobar"))
    }

    private fun launchFragment(): FragmentScenario<IdentityFragment> {
        // TODO remove scheduler hacks after this gets fixed:
        //  https://github.com/robolectric/robolectric/issues/1306#issuecomment-192641680
        Robolectric.getBackgroundThreadScheduler().pause()
        Robolectric.getForegroundThreadScheduler().pause()
        val fragment = launchFragmentInContainer<IdentityFragment>(
                themeResId = R.style.Theme_MaterialComponents_DayNight_DarkActionBar,
                factory = HomeActivity.Factory(
                        dispatchers = dispatcherRule.dispatchers,
                        auth = mockAuth,
                        api = mockApi
                )
        )
        Robolectric.getBackgroundThreadScheduler().unPause()
        Robolectric.getForegroundThreadScheduler().unPause()
        return fragment
    }
}
