package com.bymason.kiosk.checkin.feature.nda

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bymason.kiosk.checkin.core.model.Employee
import com.bymason.kiosk.checkin.core.model.Guest
import com.bymason.kiosk.checkin.helpers.TestCoroutineDispatcherRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class NdaViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val dispatcherRule = TestCoroutineDispatcherRule()

    private val mockNdaRepository = Mockito.mock(NdaRepository::class.java)
    private val vm = NdaViewModel(mockNdaRepository)

    @Test
    fun `NDA signing action is immediately kicked off on create`() = dispatcherRule.runBlocking {
        launch {
            assertThat(vm.actions.take(1).single()).isEqualTo(NdaViewModel.Action.SignNda)
        }
    }

    @Test
    fun `View NDA action is sent after signing request`() = dispatcherRule.runBlocking {
        `when`(mockNdaRepository.sign(any(), any())).thenReturn("my_url")

        vm.signNda(Guest("G", "E"))

        launch {
            assertThat(vm.viewActions.take(1).single())
                    .isEqualTo(NdaViewModel.ViewAction.VisitPage("my_url"))
        }
    }

    @Test
    fun `Finish action is sent after signing is complete`() = dispatcherRule.runBlocking {
        vm.finish(Employee("id", "n", null), Guest("G", "E"))

        launch {
            assertThat(vm.actions.take(1).single())
                    .isEqualTo(NdaViewModel.Action.Navigate(NdaFragmentDirections.reset()))
        }
    }
}
