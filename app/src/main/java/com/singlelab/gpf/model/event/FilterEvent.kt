package com.singlelab.gpf.model.event

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilterEvent(
    var distance: Distance = Distance.FAR,
    val selectedTypes: MutableList<EventType> = mutableListOf(),
    var cityId: Int? = null,
    var cityName: String? = null,
    var longitude: Double? = null,
    var latitude: Double? = null,
    var isExceptOnline: Boolean = false,
    var isOnlyOnline: Boolean = false,
    var minimalStartTime: Long? = null,
    var maximalEndTime: Long? = null
) : Parcelable {
    fun isOnlineForRequest(): Boolean? {
        return when {
            isExceptOnline -> false
            isOnlyOnline -> true
            else -> null
        }
    }

    fun isFullFilter(): Boolean {
        return distance == Distance.FAR &&
                (selectedTypes.isEmpty() || selectedTypes.size == EventType.values().size) &&
                longitude == null &&
                latitude == null &&
                !isExceptOnline &&
                !isOnlyOnline &&
                minimalStartTime == null &&
                maximalEndTime == null
    }
}