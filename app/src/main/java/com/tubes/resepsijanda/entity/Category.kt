package com.tubes.resepsijanda.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category (
    var name: String,
    var photo: String
) : Parcelable