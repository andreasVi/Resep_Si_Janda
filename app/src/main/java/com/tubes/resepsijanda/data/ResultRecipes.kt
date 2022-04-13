package com.tubes.resepsijanda.data


import com.google.gson.annotations.SerializedName

data class ResultRecipes(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("likes")
    val likes: Int,
    @SerializedName("missedIngredientCount")
    val missedIngredientCount: Int,
    @SerializedName("missedIngredients")
    val recipesData: List<RecipesData>,
    @SerializedName("title")
    val title: String,
)