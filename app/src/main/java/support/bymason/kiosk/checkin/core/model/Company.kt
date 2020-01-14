package support.bymason.kiosk.checkin.core.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class Company(
        val name: String,
        val logoUrl: String
) : Parcelable
