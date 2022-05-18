package com.tubes.resepsijanda.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    var id: Int,
    var name: String,
    var photo: String
) : Parcelable