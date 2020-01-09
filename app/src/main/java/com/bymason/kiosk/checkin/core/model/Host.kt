package com.bymason.kiosk.checkin.core.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Host(
        val id: String,
        val name: String,
        val photoUrl: String?
) : Parcelable
