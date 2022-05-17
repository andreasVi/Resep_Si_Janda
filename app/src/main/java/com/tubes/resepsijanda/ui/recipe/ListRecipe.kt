package com.tubes.resepsijanda.ui.recipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.tubes.resepsijanda.adapter.ListRecipesAdapter
import com.tubes.resepsijanda.databinding.ActivityListRecipeBinding
import com.tubes.resepsijanda.entity.Recipe
import com.tubes.resepsijanda.util.constant.Companion.compSearch
import com.tubes.resepsijanda.util.constant.Companion.discover
import com.tubes.resepsijanda.util.constant.Companion.key
import com.tubes.resepsijanda.util.constant.Companion.qty
import com.tubes.resepsijanda.util.constant.Companion.search
import com.tubes.resepsijanda.util.constant.Companion.spoonacular
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class ListRecipe : AppCompatActivity() {
    private lateinit var binding: ActivityListRecipeBinding
    companion object{
        private val TAG = ListRecipe::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvRecipes.setHasFixedSize(true)

        getRecipesfromApi()
    }

    fun getRecipesfromApi(){
        val nameDiscover = intent.getStringExtra("name_discover")
        title = nameDiscover

        val client = AsyncHttpClient()
        val listRecipes = ArrayList<Recipe>()
        val url = spoonacular + compSearch + discover + nameDiscover + qty +key
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                //Jika koneksi berhasil
                val result = String(responseBody)
                Log.d(TAG, result)
                try {
                    val responseObject = JSONObject(result)
                    val jsonArray = responseObject.getJSONArray("results")

                    for (i in 0 until jsonArray.length()){
                        val id = jsonArray.getJSONObject(i).getInt("id")
                        val title = jsonArray.getJSONObject(i).getString("title")
                        val image = jsonArray.getJSONObject(i).getString("image")

                        val recipe = Recipe(
                            id,
                            title,
                            image
                        )
                        listRecipes.add(recipe)
                    }
                    showRecyclerCardRecipes(listRecipes)
                }catch (e:Exception){
                    Toast.makeText(this@ListRecipe, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                //Jika koneksi gagal
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(this@ListRecipe, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun showRecyclerCardRecipes(listRecipes: ArrayList<Recipe>) {
        binding.rvRecipes.layoutManager = LinearLayoutManager(this)
        val cardViewRecipeAdapter = ListRecipesAdapter(listRecipes)
        binding.rvRecipes.adapter = cardViewRecipeAdapter
    }
}