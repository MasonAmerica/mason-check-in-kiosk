package com.bymason.kiosk.checkin.feature.welcome

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bymason.kiosk.checkin.core._globalContext
import com.bymason.kiosk.checkin.core.model.Company
import com.bymason.kiosk.checkin.helpers.Resettable
import com.bymason.kiosk.checkin.helpers.TestCoroutineDispatcherRule
import com.google.common.truth.Truth.assertThat
import com.google.firebase.nongmsauth.FirebaseAuthCompat
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class WelcomeViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val dispatcherRule = TestCoroutineDispatcherRule()
    @get:Rule
    val vmField = Resettable {
        val appMock = mock(Application::class.java)
        `when`(appMock.getString(anyInt(), anyString())).thenReturn("something")
        _globalContext = appMock

        WelcomeViewModel(mockAuth, mockRepository)
    }

    private val mockAuth = mock(FirebaseAuthCompat::class.java)
    private val mockRepository = mock(WelcomeRepository::class.java)
    private val vm by vmField

    @Test
    fun `Signed in user starts check-in flow`() = dispatcherRule.runBlocking {
        `when`(mockAuth.uid).thenReturn("uid")

        vm.onTap()

        launch {
            assertThat(vm.actions.take(1).single()).isEqualTo(
                    WelcomeViewModel.Action.Navigate(WelcomeFragmentDirections.next()))
        }
    }

    @Test
    fun `Signed out user is redirected to sign-in flow`() = dispatcherRule.runBlocking {
        `when`(mockAuth.uid).thenReturn(null)

        vm.onTap()

        launch {
            assertThat(vm.actions.take(1).single()).isEqualTo(
                    WelcomeViewModel.Action.Navigate(WelcomeFragmentDirections.signIn()))
        }
    }

    @Test
    fun `State is not loading without auth`() = dispatcherRule.runBlocking {
        `when`(mockAuth.uid).thenReturn(null)

        dispatcherRule.pauseDispatcher {
            assertThat(vm.state.value?.isLoading).isFalse()
        }
    }

    @Test
    fun `State is loading with auth`() = dispatcherRule.runBlocking {
        `when`(mockAuth.uid).thenReturn("uid")

        dispatcherRule.pauseDispatcher {
            assertThat(vm.state.value?.isLoading).isTrue()
        }
    }

    @Test
    fun `Company metadata is loaded`() = dispatcherRule.runBlocking {
        `when`(mockAuth.uid).thenReturn("uid")
        `when`(mockRepository.getCompanyMetadata()).thenReturn(Company("Mason", "url"))

        assertThat(vm.state.value?.isLoading).isFalse()
        assertThat(vm.state.value?.companyLogoUrl).isEqualTo("url")
    }
}
