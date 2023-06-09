package com.singlelab.gpf.model.profile

import android.os.Parcelable
import com.singlelab.gpf.model.Const
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilterPerson(
    var cityId: Int? = null,
    var cityName: String? = null,
    var minAge: Int = Const.MIN_AGE,
    var maxAge: Int = Const.MAX_AGE
) : Parcelable