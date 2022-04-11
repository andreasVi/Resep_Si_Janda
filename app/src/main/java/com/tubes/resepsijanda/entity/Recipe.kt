package com.tubes.resepsijanda.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    var category: String,
    var photo: String
) : Parcelable
