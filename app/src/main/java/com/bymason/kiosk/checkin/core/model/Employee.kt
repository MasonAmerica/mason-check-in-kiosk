package com.bymason.kiosk.checkin.core.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Employee(
        val id: String,
        val name: String,
        val email: String,
        val photoUrl: String?
) : Parcelable
