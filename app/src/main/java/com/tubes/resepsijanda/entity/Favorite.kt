package com.tubes.resepsijanda.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Favorite (
    var favorite: String,
    var photo: String
) : Parcelable