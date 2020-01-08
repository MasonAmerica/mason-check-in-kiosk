package com.bymason.kiosk.checkin.feature.nda

import androidx.core.view.isVisible
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bymason.kiosk.checkin.CheckInNavHostFragment
import com.bymason.kiosk.checkin.R
import com.bymason.kiosk.checkin.databinding.NdaFragmentBinding
import com.google.common.truth.Truth.assertThat
import com.google.firebase.nongmsauth.FirebaseAuthCompat
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class NdaFragmentTest {
    private val mockAuth = mock(FirebaseAuthCompat::class.java)
    private val mockRepository = mock(NdaRepository::class.java)

    @Test
    fun `NDA signing page loads on create`() {
        runBlocking {
            `when`(mockRepository.sign(any())).thenReturn("https://google.com")
        }

        val scenario = launchFragment()
        scenario.onFragment { fragment ->
            val binding = NdaFragmentBinding.bind(fragment.requireView())

            assertThat(binding.web.url).isEqualTo("https://google.com")
        }
    }


    @Test
    fun `Finish check-in button finishes check-in`() {
        runBlocking {
            `when`(mockRepository.sign(any())).thenReturn("https://google.com")
        }

        val mockNavController = mock(NavController::class.java)
        val scenario = launchFragment()
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)

            val binding = NdaFragmentBinding.bind(fragment.requireView())

            binding.web.isVisible = false
            binding.finishCheckInHint.isVisible = true

            onView(withId(R.id.finish_check_in_hint)).perform(click())
        }

        runBlocking {
            verify(mockRepository).finish(any())
        }
        verify(mockNavController).navigate(NdaFragmentDirections.reset())
    }

    private fun launchFragment(
            sessionId: String = "mySession"
    ) = launchFragmentInContainer<NdaFragment>(
            NdaFragmentArgs(sessionId).toBundle(),
            R.style.Theme_MaterialComponents_DayNight_DarkActionBar,
            CheckInNavHostFragment.Factory(
                    auth = mockAuth,
                    ndaRepository = mockRepository
            )
    )
}
