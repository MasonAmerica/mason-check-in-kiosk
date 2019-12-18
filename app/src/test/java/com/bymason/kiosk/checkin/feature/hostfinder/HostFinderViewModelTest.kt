package com.bymason.kiosk.checkin.feature.hostfinder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bymason.kiosk.checkin.core.model.Host
import com.bymason.kiosk.checkin.helpers.TestCoroutineDispatcherRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify

class HostFinderViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val dispatcherRule = TestCoroutineDispatcherRule()

    private val mockHostRepository = mock(HostRepository::class.java)
    private val vm = HostFinderViewModel(mockHostRepository, "mySession")

    @Test
    fun `Searching for null name noops with empty hosts`() = dispatcherRule.runBlocking {
        vm.onSearch(null)

        verify(mockHostRepository, never()).find(any())
        assertThat(vm.state.value?.hosts).isEmpty()
    }

    @Test
    fun `Searching for empty name noops with empty hosts`() = dispatcherRule.runBlocking {
        vm.onSearch(" ")

        verify(mockHostRepository, never()).find(any())
        assertThat(vm.state.value?.hosts).isEmpty()
    }

    @Test
    fun `Searching for valid name lists hosts`() = dispatcherRule.runBlocking {
        val host = Host("id", "Mr Robot", null)
        `when`(mockHostRepository.find(any())).thenReturn(listOf(host))

        vm.onSearch("Mr")

        assertThat(vm.state.value?.hosts).containsExactly(host)
    }

    @Test
    fun `Searching for two hosts in a row cancels previous search`(
    ) = dispatcherRule.runBlocking {
        val host1 = Host("id1", "Mr Robot", null)
        val host2 = Host("id2", "Mr Robot", null)
        val result1 = CompletableDeferred<List<Host>>()
        val result2 = CompletableDeferred<List<Host>>()
        val vm = HostFinderViewModel(object : HostRepository {
            override suspend fun find(name: String) = when (name) {
                "1" -> result1
                "2" -> result2
                else -> error("Unknown $name")
            }.await()

            override suspend fun registerHost(
                    sessionId: String,
                    host: Host
            ) = error("Not implemented")
        }, "mySession")

        vm.onSearch("1")
        assertThat(vm.state.value?.hosts).isEmpty()

        vm.onSearch("2")
        result2.complete(listOf(host2))
        assertThat(vm.state.value?.hosts).containsExactly(host2)

        result1.complete(listOf(host1))
        assertThat(vm.state.value?.hosts).containsExactly(host2)
    }

    @Test
    fun `Finding host navigates to next destination`() = dispatcherRule.runBlocking {
        val host = Host("id", "Mr Robot", null)
        `when`(mockHostRepository.registerHost(any(), any())).thenReturn("foobar")

        vm.onFound(host)

        assertThat(vm.state.value?.isLoading).isFalse()
        launch {
            assertThat(vm.actions.take(1).single()).isEqualTo(
                    HostFinderViewModel.Action.Navigate(HostFinderFragmentDirections.next("foobar")))
        }
    }

    @Test
    fun `Finding host navigates to next destinations`() = dispatcherRule.runBlocking {
        val result1 = CompletableDeferred<String>()
        val result2 = CompletableDeferred<String>()
        val host1 = Host("id1", "Mr Robot", null)
        val host2 = Host("id2", "Mr Robot", null)
        val vm = HostFinderViewModel(object : HostRepository {
            override suspend fun find(name: String) = error("Not implemented")

            override suspend fun registerHost(
                    sessionId: String,
                    host: Host
            ) = when {
                host == host1 -> result1
                host == host2 -> result2
                else -> error("Unknown: $host")
            }.await()
        }, "mySession2")

        vm.onFound(host1)
        vm.onFound(host2)

        result1.complete("1")
        result2.complete("2")

        launch {
            assertThat(vm.actions.take(1).single()).isEqualTo(
                    HostFinderViewModel.Action.Navigate(HostFinderFragmentDirections.next("1")))
        }
    }
}
