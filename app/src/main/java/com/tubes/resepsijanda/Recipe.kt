package com.tubes.resepsijanda

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(
    var name: String,
    var category: String,
    var recipe: String,
    var photo: String
) : Parcelable
