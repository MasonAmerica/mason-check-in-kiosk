package support.bymason.kiosk.checkin.feature.welcome

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.google.firebase.nongmsauth.FirebaseAuthCompat
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import support.bymason.kiosk.checkin.helpers.Resettable
import support.bymason.kiosk.checkin.helpers.TestCoroutineDispatcherRule

class WelcomeViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val dispatcherRule = TestCoroutineDispatcherRule()
    @get:Rule
    val vmField = Resettable {
        WelcomeViewModel(mockAuth)
    }

    private val mockAuth = mock(FirebaseAuthCompat::class.java)
    private val vm by vmField

    @Test
    fun `Signed in user starts check-in flow`() = dispatcherRule.runBlocking {
        `when`(mockAuth.uid).thenReturn("uid")

        launch {
            assertThat(vm.actions.take(1).single()).isEqualTo(
                    WelcomeViewModel.Action.Navigate(WelcomeFragmentDirections.next()))
        }
    }

    @Test
    fun `Signed out user is redirected to sign-in flow`() = dispatcherRule.runBlocking {
        `when`(mockAuth.uid).thenReturn(null)

        launch {
            assertThat(vm.actions.take(1).single()).isEqualTo(
                    WelcomeViewModel.Action.Navigate(WelcomeFragmentDirections.signIn()))
        }
    }
}
