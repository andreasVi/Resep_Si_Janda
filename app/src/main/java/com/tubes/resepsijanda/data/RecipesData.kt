package com.tubes.resepsijanda.data


import com.google.gson.annotations.SerializedName

data class RecipesData(
    @SerializedName("aisle")
    val aisle: String,
    @SerializedName("amount")
    val amount: Int,
    @SerializedName("extendedName")
    val extendedName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("meta")
    val meta: List<String>,
    @SerializedName("name")
    val name: String,
    @SerializedName("original")
    val original: String,
    @SerializedName("originalName")
    val originalName: String,
)