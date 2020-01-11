package com.bymason.kiosk.checkin.feature.report

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bymason.kiosk.checkin.helpers.Resettable
import com.bymason.kiosk.checkin.helpers.TestCoroutineDispatcherRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import org.junit.Rule
import org.junit.Test

class ReportViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val dispatcherRule = TestCoroutineDispatcherRule()
    @get:Rule
    val vmField = Resettable {
        ReportViewModel()
    }

    private val vm by vmField

    @Test
    fun `Continue sends reset action`() = dispatcherRule.runBlocking {
        vm.onContinue()

        launch {
            assertThat(vm.actions.take(1).single()).isEqualTo(
                    ReportViewModel.Action.Navigate(ReportFragmentDirections.reset()))
        }
    }

    @Test
    fun `Continue action is sent after timeout`() = dispatcherRule.runBlocking {
        launch {
            assertThat(vm.actions.take(1).single()).isEqualTo(
                    ReportViewModel.Action.Navigate(ReportFragmentDirections.reset()))
        }
    }
}
