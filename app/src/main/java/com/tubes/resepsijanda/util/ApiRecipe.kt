package com.tubes.resepsijanda.util


import com.tubes.resepsijanda.data.RecipesData
import com.tubes.resepsijanda.util.Constants.Companion.API_KEY
import retrofit2.Call
import retrofit2.http.GET

interface ApiRecipe {
    @GET("/recipes/complexSearch?number=3type=salad" + API_KEY)
    fun getRecipe() : Call<List<RecipesData>>
}