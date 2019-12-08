package com.bymason.kiosk.checkin

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bymason.kiosk.checkin.core.data.Auth
import com.bymason.kiosk.checkin.helpers.TestCoroutineDispatcherRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class WelcomeFragmentViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val dispatcherRule = TestCoroutineDispatcherRule()

    private val mockAuth = mock(Auth::class.java)
    private val vm = WelcomeFragmentViewModel(mockAuth)

    @Test
    fun `Signed in user starts check-in flow`() = dispatcherRule.runBlocking {
        `when`(mockAuth.isSignedIn).thenReturn(true)

        vm.start()

        launch {
            assertThat(vm.navEvents.take(1).single())
                    .isEqualTo(WelcomeFragmentDirections.next())
        }
    }

    @Test
    fun `Signed out user is redirected to sign-in flow`() = dispatcherRule.runBlocking {
        `when`(mockAuth.isSignedIn).thenReturn(false)

        vm.start()

        launch {
            assertThat(vm.intentEvents.take(1).single()).isNotNull()
        }
    }
}
