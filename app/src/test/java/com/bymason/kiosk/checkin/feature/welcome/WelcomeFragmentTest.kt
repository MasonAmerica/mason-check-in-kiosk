package com.bymason.kiosk.checkin.feature.welcome

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bymason.kiosk.checkin.CheckInNavHostFragment
import com.bymason.kiosk.checkin.R
import com.bymason.kiosk.checkin.core.model.Company
import com.bymason.kiosk.checkin.databinding.WelcomeFragmentBinding
import com.google.common.truth.Truth.assertThat
import com.google.firebase.nongmsauth.FirebaseAuthCompat
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.robolectric.Robolectric

@RunWith(AndroidJUnit4::class)
class WelcomeFragmentTest {
    private val mockAuth = mock(FirebaseAuthCompat::class.java)
    private val mockRepository = mock(WelcomeRepository::class.java)

    @Test
    fun `Clicking anywhere moves to the next screen`() {
        `when`(mockAuth.uid).thenReturn("uid")
        runBlocking {
            `when`(mockRepository.getCompanyMetadata()).thenReturn(Company("Mason", "url"))
        }

        val mockNavController = mock(NavController::class.java)
        val scenario = launchFragment()
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }

        onView(withId(R.id.root)).perform(click())

        verify(mockNavController).navigate(WelcomeFragmentDirections.next())
    }

    @Test
    fun `Company metadata is populated on launch`() {
        `when`(mockAuth.uid).thenReturn("uid")
        runBlocking {
            `when`(mockRepository.getCompanyMetadata()).thenReturn(Company("Mason", "url"))
        }

        val scenario = launchFragment()
        scenario.onFragment { fragment ->
            val binding = WelcomeFragmentBinding.bind(fragment.requireView())

            assertThat(binding.welcomeTitle.text.toString()).contains("Mason")
        }
    }

    private fun launchFragment(
    ): FragmentScenario<WelcomeFragment> {
        // TODO remove scheduler hacks after this gets fixed:
        //  https://github.com/robolectric/robolectric/issues/1306#issuecomment-192641680
        Robolectric.getBackgroundThreadScheduler().pause()
        Robolectric.getForegroundThreadScheduler().pause()
        val fragment = launchFragmentInContainer<WelcomeFragment>(
                themeResId = R.style.Theme_MaterialComponents_DayNight_DarkActionBar,
                factory = CheckInNavHostFragment.Factory(
                        auth = mockAuth,
                        welcomeRepository = mockRepository
                )
        )
        Robolectric.getBackgroundThreadScheduler().unPause()
        Robolectric.getForegroundThreadScheduler().unPause()
        return fragment
    }
}
