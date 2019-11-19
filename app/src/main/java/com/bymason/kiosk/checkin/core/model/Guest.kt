package com.bymason.kiosk.checkin.core.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Guest(
        val name: String,
        val email: String
) : Parcelable
