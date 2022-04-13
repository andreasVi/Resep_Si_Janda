package com.tubes.resepsijanda.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.tubes.resepsijanda.R
import com.tubes.resepsijanda.adapter.AdapterRecipe
import com.tubes.resepsijanda.data.ResultRecipes
import com.tubes.resepsijanda.util.NetworkConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeActivity : AppCompatActivity() {
    private lateinit var rvRecipes: RecyclerView

    companion object{
        private val TAG = RecipeActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        rvRecipes = findViewById(R.id.rv_recipe)

        getRecipes()
    // penggunaan api spoonacular ?apiKey=4b526d8bbcca475cbaaee525f01065db
    }

    fun getRecipes(){
        NetworkConfig().getService()
            .getRecipe()
            .enqueue(object : Callback<ArrayList<ResultRecipes>> { //Masih error
                override fun onResponse(
                    call: Call<ArrayList<ResultRecipes>>,
                    response: Response<ArrayList<ResultRecipes>>
                ) {
                    rvRecipes.adapter = AdapterRecipe(response.body())
                }

                override fun onFailure(call: Call<ArrayList<ResultRecipes>>, t: Throwable) {
                    Toast.makeText(this@RecipeActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
                }

            })
    }


//    fun getRecipe(){
//        val client = AsyncHttpClient()
//        val url = BASE_URL
//        client.get(url, object : AsyncHttpResponseHandler() {
//            override fun onSuccess(statusCode: Int, headers: Array<Header>, responseBody: ByteArray) {
//                val listRecipe = ArrayList<RecipeActivity>()
//
//                val result = String(responseBody)
//                Log.d(TAG, result)
//
//                try {
//                    val jsonArray = JSONArray(result)
//
//                    for (i in 0 until jsonArray.length()){
//                        val jsonObject = jsonArray
//
////                        val id = jsonObject.getString("id")
////                        val title = jsonObject.getString("title")
////                        val photo = jsonObject.getString("photo")
////                        listRecipe.add()
//                    }
//                } catch (e: Exception) {
//                    Toast.makeText(this@RecipeActivity, e.message, Toast.LENGTH_SHORT).show()
//                    e.printStackTrace()
//                }
//            }
//
//            override fun onFailure(statusCode: Int, headers: Array<Header>, responseBody: ByteArray, error: Throwable) {
//                // Jika koneksi gagal
//                val errorMessage = when (statusCode){
//                    401 -> "$statusCode : Bad Request"
//                    403 -> "$statusCode : Forbidden"
//                    404 -> "$statusCode : Not Found"
//                    else -> "$statusCode : ${error.message}"
//                }
//                Toast.makeText(this@RecipeActivity, errorMessage, Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
}
