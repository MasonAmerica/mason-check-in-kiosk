package com.bymason.kiosk.checkin.feature.identity

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bymason.kiosk.checkin.core.model.GuestField
import com.bymason.kiosk.checkin.core.model.GuestFieldType
import com.bymason.kiosk.checkin.helpers.Resettable
import com.bymason.kiosk.checkin.helpers.TestCoroutineDispatcherRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class IdentityViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val dispatcherRule = TestCoroutineDispatcherRule()
    @get:Rule
    val vmField = Resettable {
        IdentityViewModel(mockIdentityRepository, dispatcherRule.dispatchers)
    }

    private val mockIdentityRepository = mock(IdentityRepository::class.java)
    private val vm by vmField

    @Test
    fun `State is loading on start`() = dispatcherRule.runBlocking {
        dispatcherRule.pauseDispatcher {
            assertThat(vm.state.value?.isLoading).isTrue()
        }
    }

    @Test
    fun `Guest fields are loaded`() = dispatcherRule.runBlocking {
        val state = FieldState(GuestField("id", GuestFieldType.NAME, "foo", false, null))
        `when`(mockIdentityRepository.getGuestFields()).thenReturn(listOf(state))

        assertThat(vm.state.value?.isLoading).isFalse()
        assertThat(vm.state.value?.fieldStates).containsExactly(state)
    }

    @Test
    fun `Guest fields with regex are processed`() = dispatcherRule.runBlocking {
        val state = FieldState(GuestField("id", GuestFieldType.NAME, "foo", true, ".+"))
        `when`(mockIdentityRepository.getGuestFields()).thenReturn(listOf(state))

        assertThat(vm.state.value?.isLoading).isFalse()
        assertThat(vm.state.value?.fieldStates)
                .containsExactly(state.copy(hasError = true, showError = false))
    }

    @Test
    fun `Invalid field gaining focus marks field as interacted with`() = dispatcherRule.runBlocking {
        val state = FieldState(GuestField("id", GuestFieldType.NAME, "foo", true, ".+"))
        `when`(mockIdentityRepository.getGuestFields()).thenReturn(listOf(state))

        vm.onFieldChanged(state, null, true)

        assertThat(vm.state.value?.fieldStates?.single()?.hasInteracted).isTrue()
    }

    @Test
    fun `Invalid field losing focus shows error`() = dispatcherRule.runBlocking {
        val state = FieldState(GuestField("id", GuestFieldType.NAME, "foo", true, ".+"))
        `when`(mockIdentityRepository.getGuestFields()).thenReturn(listOf(state))

        vm.onFieldChanged(state, null, true)
        vm.onFieldChanged(state, null, false)

        assertThat(vm.state.value?.fieldStates?.single()?.hasError).isTrue()
        assertThat(vm.state.value?.fieldStates?.single()?.showError).isTrue()
    }

    @Test
    fun `Invalid field re-gaining focus hides error`() = dispatcherRule.runBlocking {
        val state = FieldState(GuestField("id", GuestFieldType.NAME, "foo", true, ".+"))
        `when`(mockIdentityRepository.getGuestFields()).thenReturn(listOf(state))

        vm.onFieldChanged(state, null, true)
        vm.onFieldChanged(state, null, false)
        vm.onFieldChanged(state, null, true)

        assertThat(vm.state.value?.fieldStates?.single()?.hasError).isTrue()
        assertThat(vm.state.value?.fieldStates?.single()?.showError).isFalse()
    }


    @Test
    fun `Updating field with valid value processes correctly`() = dispatcherRule.runBlocking {
        val state = FieldState(GuestField("id", GuestFieldType.NAME, "foo", true, ".+"))
        `when`(mockIdentityRepository.getGuestFields()).thenReturn(listOf(state))

        vm.onFieldChanged(state, "goo", false)

        assertThat(vm.state.value?.fieldStates?.single()?.value).isEqualTo("goo")
        assertThat(vm.state.value?.fieldStates?.single()?.hasError).isFalse()
        assertThat(vm.state.value?.fieldStates?.single()?.showError).isFalse()
    }

    @Test
    fun `Empty field with no regex is valid`() = dispatcherRule.runBlocking {
        val state = FieldState(GuestField("id", GuestFieldType.NAME, "foo", true, null))
        `when`(mockIdentityRepository.getGuestFields()).thenReturn(listOf(state))

        vm.onFieldChanged(state, null, false)

        assertThat(vm.state.value?.fieldStates?.single()?.hasError).isFalse()
    }

    @Test
    fun `Field with no regex is valid`() = dispatcherRule.runBlocking {
        val state = FieldState(GuestField("id", GuestFieldType.NAME, "foo", true, null))
        `when`(mockIdentityRepository.getGuestFields()).thenReturn(listOf(state))

        vm.onFieldChanged(state, "foo", false)

        assertThat(vm.state.value?.fieldStates?.single()?.hasError).isFalse()
    }

    @Test
    fun `Empty optional field with regex is valid`() = dispatcherRule.runBlocking {
        val state = FieldState(GuestField("id", GuestFieldType.NAME, "foo", false, ".+"))
        `when`(mockIdentityRepository.getGuestFields()).thenReturn(listOf(state))

        vm.onFieldChanged(state, null, false)

        assertThat(vm.state.value?.fieldStates?.single()?.hasError).isFalse()
    }

    @Test
    fun `Invalid optional field with regex is invalid`() = dispatcherRule.runBlocking {
        val state = FieldState(GuestField("id", GuestFieldType.NAME, "foo", false, ".+"))
        `when`(mockIdentityRepository.getGuestFields()).thenReturn(listOf(state))

        vm.onFieldChanged(state, "", false)

        assertThat(vm.state.value?.fieldStates?.single()?.hasError).isTrue()
    }

    @Test
    fun `Optional field with regex is valid`() = dispatcherRule.runBlocking {
        val state = FieldState(GuestField("id", GuestFieldType.NAME, "foo", false, ".+"))
        `when`(mockIdentityRepository.getGuestFields()).thenReturn(listOf(state))

        vm.onFieldChanged(state, "foo", false)

        assertThat(vm.state.value?.fieldStates?.single()?.hasError).isFalse()
    }

    @Test
    fun `Empty required field with regex is invalid`() = dispatcherRule.runBlocking {
        val state = FieldState(GuestField("id", GuestFieldType.NAME, "foo", true, ".+"))
        `when`(mockIdentityRepository.getGuestFields()).thenReturn(listOf(state))

        vm.onFieldChanged(state, null, false)

        assertThat(vm.state.value?.fieldStates?.single()?.hasError).isTrue()
    }

    @Test
    fun `Invalid required field with regex is invalid`() = dispatcherRule.runBlocking {
        val state = FieldState(GuestField("id", GuestFieldType.NAME, "foo", true, ".+"))
        `when`(mockIdentityRepository.getGuestFields()).thenReturn(listOf(state))

        vm.onFieldChanged(state, "", false)

        assertThat(vm.state.value?.fieldStates?.single()?.hasError).isTrue()
    }

    @Test
    fun `Required field with regex is valid`() = dispatcherRule.runBlocking {
        val state = FieldState(GuestField("id", GuestFieldType.NAME, "foo", true, ".+"))
        `when`(mockIdentityRepository.getGuestFields()).thenReturn(listOf(state))

        vm.onFieldChanged(state, "foo", false)

        assertThat(vm.state.value?.fieldStates?.single()?.hasError).isFalse()
    }

    @Test
    fun `Continue button is disabled while fields are invalid`() = dispatcherRule.runBlocking {
        val state = FieldState(GuestField("id", GuestFieldType.NAME, "foo", true, ".+"))
        `when`(mockIdentityRepository.getGuestFields()).thenReturn(listOf(state))

        vm.onFieldChanged(state, null, true)

        assertThat(vm.state.value?.isContinueButtonEnabled).isFalse()
    }

    @Test
    fun `Continue button is enabled when all fields are valid`() = dispatcherRule.runBlocking {
        val state = FieldState(GuestField("id", GuestFieldType.NAME, "foo", true, ".+"))
        `when`(mockIdentityRepository.getGuestFields()).thenReturn(listOf(state))

        vm.onFieldChanged(state, "foo", true)

        assertThat(vm.state.value?.isContinueButtonEnabled).isTrue()
    }

    @Test
    fun `Continuing navigates to next destination`() = dispatcherRule.runBlocking {
        `when`(mockIdentityRepository.registerFields(any())).thenReturn("foobar")

        vm.onContinue()

        assertThat(vm.state.value?.isLoading).isFalse()
        launch {
            assertThat(vm.actions.take(1).single()).isEqualTo(
                    IdentityViewModel.Action.Navigate(IdentityFragmentDirections.next("foobar")))
        }
    }

    @Test
    fun `Finding employee navigates to next destinations`() = dispatcherRule.runBlocking {
        val result1 = CompletableDeferred<String>()
        val result2 = CompletableDeferred<String>()
        val field = FieldState(GuestField("id", GuestFieldType.NAME, "foo", true, ".+"))
        val vm = IdentityViewModel(object : IdentityRepository {
            override suspend fun getGuestFields() = listOf(field)

            override suspend fun registerFields(fields: List<FieldState>) = when {
                fields.single().value == null -> result1
                fields.single().value == "next" -> result2
                else -> error("Unknown: $fields")
            }.await()
        }, dispatcherRule.dispatchers)

        vm.onContinue()
        vm.onFieldChanged(field, "next", false)
        vm.onContinue()

        result1.complete("1")
        result2.complete("2")

        launch {
            assertThat(vm.actions.take(1).single()).isEqualTo(
                    IdentityViewModel.Action.Navigate(IdentityFragmentDirections.next("1")))
        }
    }
}
