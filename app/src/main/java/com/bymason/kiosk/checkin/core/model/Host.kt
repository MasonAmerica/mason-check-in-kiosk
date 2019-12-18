package com.bymason.kiosk.checkin.core.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Host(
        val id: String,
        val name: String,
        val photoUrl: String?
) : Parcelable
