package support.bymason.kiosk.checkin.core.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class GuestField(
        val id: String,
        val type: GuestFieldType,
        val name: String,
        val required: Boolean,
        val regex: String?
) : Parcelable
