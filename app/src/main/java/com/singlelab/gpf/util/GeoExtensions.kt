package com.singlelab.gpf.util

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.singlelab.gpf.model.Const
import java.io.IOException

fun Context.getLocationName(xCoordinate: Double?, yCoordinate: Double?): String? {
    val geoCoder = Geocoder(this, Const.RUS_LOCALE)
    if (xCoordinate == null || yCoordinate == null) {
        return null
    }
    return try {
        val addresses: List<Address> = geoCoder.getFromLocation(xCoordinate, yCoordinate, 1)
        if (addresses.isNotEmpty()) {
            addresses[0].getAddressLine(0).removePostalCode(addresses[0].postalCode)
        } else {
            null
        }
    } catch (e: IOException) {
        null
    }
}