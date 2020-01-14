package support.bymason.kiosk.checkin.feature.identity

import support.bymason.kiosk.checkin.core.model.GuestField

data class FieldState(
        val field: GuestField,
        val value: String? = null,
        val hasError: Boolean = false,
        val showError: Boolean = false,
        val hasInteracted: Boolean = false
)
