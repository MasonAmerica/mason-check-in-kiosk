package com.bymason.kiosk.checkin.feature.identity

import androidx.core.util.PatternsCompat
import androidx.lifecycle.ViewModel

class IdentityViewModel : ViewModel() {
    fun isNameValid(name: String?): Boolean = !name.isNullOrEmpty()

    fun isEmailValid(email: String?): Boolean =
            PatternsCompat.EMAIL_ADDRESS.matcher(email.orEmpty()).matches()
}
