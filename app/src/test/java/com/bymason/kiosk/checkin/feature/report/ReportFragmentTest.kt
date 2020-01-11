package com.bymason.kiosk.checkin.feature.report

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.pressBack
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bymason.kiosk.checkin.CheckInNavHostFragment
import com.bymason.kiosk.checkin.R
import com.bymason.kiosk.checkin.core.data.CheckInApi
import com.bymason.kiosk.checkin.databinding.ReportFragmentBinding
import com.bymason.kiosk.checkin.helpers.TestCoroutineDispatcherRule
import com.google.firebase.nongmsauth.FirebaseAuthCompat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class ReportFragmentTest {
    @get:Rule
    val dispatcherRule = TestCoroutineDispatcherRule()

    private val mockAuth = mock(FirebaseAuthCompat::class.java)
    private val mockApi = mock(CheckInApi::class.java)

    @Test
    fun `Back press resets check-in`() {
        val mockNavController = mock(NavController::class.java)
        val scenario = launchFragment()
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }

        pressBack()
        verify(mockNavController).navigate(ReportFragmentDirections.reset())
        dispatcherRule.advanceUntilIdle()
    }

    @Test
    fun `Continue button click resets check-in`() {
        val mockNavController = mock(NavController::class.java)
        val scenario = launchFragment()
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
            val binding = ReportFragmentBinding.bind(fragment.requireView())

            binding.next.performClick()
        }

        verify(mockNavController).navigate(ReportFragmentDirections.reset())
        dispatcherRule.advanceUntilIdle()
    }

    @Test
    fun `Check-in is reset after timeout`() {
        val mockNavController = mock(NavController::class.java)
        val scenario = launchFragment()
        scenario.onFragment { fragment ->
            Navigation.setViewNavController(fragment.requireView(), mockNavController)
        }

        verify(mockNavController, never()).navigate(ReportFragmentDirections.reset())
        dispatcherRule.advanceUntilIdle()
        verify(mockNavController).navigate(ReportFragmentDirections.reset())
    }

    private fun launchFragment(
    ) = launchFragmentInContainer<ReportFragment>(
            null,
            R.style.Theme_MaterialComponents_DayNight_DarkActionBar,
            CheckInNavHostFragment.Factory(
                    dispatchers = dispatcherRule.dispatchers,
                    auth = mockAuth,
                    api = mockApi
            )
    )
}
