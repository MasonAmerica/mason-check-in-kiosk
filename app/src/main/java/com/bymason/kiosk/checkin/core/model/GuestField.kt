package com.bymason.kiosk.checkin.core.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GuestField(
        val id: String,
        val type: GuestFieldType,
        val name: String,
        val required: Boolean,
        val regex: String?
) : Parcelable
