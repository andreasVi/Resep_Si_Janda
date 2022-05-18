package com.tubes.resepsijanda.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Favorite (
    var id: Int,
    var name: String,
    var photo: String
) : Parcelable