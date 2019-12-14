package com.bymason.kiosk.checkin.core.model

enum class GuestFieldType(val value: Int) {
    NAME(0),
    EMAIL(1),
    COMPANY(2);

    companion object {
        fun from(value: Int) = values().single { it.value == value }
    }
}
