package com.bymason.kiosk.checkin

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bymason.kiosk.checkin.core.data.Auth
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class WelcomeFragmentViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val mockAuth = mock(Auth::class.java)
    private val vm = WelcomeFragmentViewModel(mockAuth)

    @Test
    fun `Signed in user starts check-in flow`() {
        `when`(mockAuth.isSignedIn).thenReturn(true)

        vm.start()

        assertThat(vm.navEvents.value).isEqualTo(WelcomeFragmentDirections.next())
        assertThat(vm.intentEvents.value).isNull()
    }

    @Test
    fun `Signed out user is redirected to sign-in flow`() {
        `when`(mockAuth.isSignedIn).thenReturn(false)

        vm.start()

        assertThat(vm.navEvents.value).isNull()
        assertThat(vm.intentEvents.value).isNotNull()
    }
}
