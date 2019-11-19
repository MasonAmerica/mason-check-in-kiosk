package com.bymason.kiosk.checkin.feature.identity

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class IdentityViewModelTest {
    private val vm = IdentityViewModel()

    @Test
    fun `Null name is invalid`() {
        val valid = vm.isNameValid(null)

        assertThat(valid).isFalse()
    }

    @Test
    fun `Empty name is invalid`() {
        val valid = vm.isNameValid("")

        assertThat(valid).isFalse()
    }

    @Test
    fun `Non-empty name is valid`() {
        val valid = vm.isNameValid("Alex")

        assertThat(valid).isTrue()
    }

    @Test
    fun `Null email is invalid`() {
        val valid = vm.isEmailValid(null)

        assertThat(valid).isFalse()
    }

    @Test
    fun `Empty email is invalid`() {
        val valid = vm.isEmailValid("")

        assertThat(valid).isFalse()
    }

    @Test
    fun `Non-empty invalid email is invalid`() {
        val valid = vm.isEmailValid("email@")

        assertThat(valid).isFalse()
    }

    @Test
    fun `Non-empty valid email is valid`() {
        val valid = vm.isEmailValid("foobar@example.com")

        assertThat(valid).isTrue()
    }
}
