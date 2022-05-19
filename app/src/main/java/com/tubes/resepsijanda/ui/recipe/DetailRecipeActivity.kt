package com.tubes.resepsijanda.ui.recipe

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.tubes.resepsijanda.R
import com.tubes.resepsijanda.adapter.ListIngredientsAdapter
import com.tubes.resepsijanda.databinding.ActivityDetailRecipeBinding
import com.tubes.resepsijanda.entity.Ingredients
import com.tubes.resepsijanda.util.constant
import com.tubes.resepsijanda.util.constant.Companion.Andreas
import com.tubes.resepsijanda.util.constant.Companion.information
import cz.msebera.android.httpclient.Header
import org.json.JSONObject


class DetailRecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailRecipeBinding
//    lateinit var title: String
//    lateinit var image: String
    companion object{
        private val TAG = DetailRecipeActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvIngredients.setHasFixedSize(true)

        getDetailRecipe()
    }

    private fun getDetailRecipe(){
        Log.d(TAG, "Menampilkan detail resep dari list resep yang dipilih")
        if (intent.hasExtra("id_recipe")){
            Log.d(TAG, "Ada data dalam intent")

            val idRecipe = intent.getIntExtra("id_recipe",0)
            Log.d(TAG, idRecipe.toString())

            getRecipe(idRecipe)
        }
    }

    private fun getRecipe(id: Int){

        val listIngredients = ArrayList<Ingredients>()
        val client = AsyncHttpClient()
        //URL untuk menampilkan detail resep berdasarkan id
        val url = constant.spoonacular + id + information + constant.key
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

                    //Mengambil data yang dibutuhkan dari API
                    val title = responseObject.getString("title")
                    val image = responseObject.getString("image")
                    val readyInMinutes = responseObject.getString("readyInMinutes")
                    val summary = responseObject.getString("summary")
                    val instructions = responseObject.getString("instructions")
                    val serving = responseObject.getString("servings")

                    //Menampilkan data yang diambil dari API ke activity detail recipe
                    //Menampilkan nama resep
                    val name: TextView = findViewById(R.id.name_recipe)
                    name.text = title

                    //Menampilkan image ke activity
                    val imageTemp: ImageView = findViewById(R.id.img_recipe)
                    Glide.with(this@DetailRecipeActivity)
                        .load(image)
                        .error(R.drawable.logo)
                        .into(imageTemp)

                    //Menampilkan waktu yang dibutuhkan untuk membuat resep ini
                    val time: TextView = findViewById(R.id.tv_time)
                    time.text = String.format(getString(R.string.minutes), readyInMinutes)

                    //Deskripsi mengenai resep ini
                    val description: TextView = findViewById(R.id.tv_description)
                    description.text = Html.fromHtml(summary)

                    val serve: TextView = findViewById(R.id.tv_serving)
                    serve.text = String.format(getString(R.string.serving), serving)

                    //Untuk mengambil data ingredients
                    val jsonArrayIngredient = responseObject.getJSONArray("extendedIngredients")
                    for (i in 0 until jsonArrayIngredient.length()){
                        val idIngredients = jsonArrayIngredient.getJSONObject(i).getInt("id")
                        val nameIngredients = jsonArrayIngredient.getJSONObject(i).getString("name")
                        val imgIngredients = "https://spoonacular.com/cdn/ingredients_100x100/" + jsonArrayIngredient.getJSONObject(i).getString("image")
                        val amountIngredients = jsonArrayIngredient.getJSONObject(i).getDouble("amount")
                        val unitIngredients = jsonArrayIngredient.getJSONObject(i).getString("unit")

                        val ingredients = Ingredients(
                            idIngredients,
                            nameIngredients,
                            imgIngredients,
                            amountIngredients,
                            unitIngredients
                        )
                        Log.d(TAG, imgIngredients)
                        listIngredients.add(ingredients)
                    }
                    showRecyclerListIngredients(listIngredients)

                    //Set instruction
                    val instruction: TextView = findViewById(R.id.tv_instructions)
                    if (instructions == "null"){
                        instruction.text = Html.fromHtml("<i>There is no Instruction</i>")
                    }else{
                        instruction.text = Html.fromHtml(instructions)
                    }
                    Log.d(TAG, instruction.toString())

                }catch (e:Exception){
                    Toast.makeText(this@DetailRecipeActivity, e.message, Toast.LENGTH_SHORT).show()
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
                Toast.makeText(this@DetailRecipeActivity, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun showRecyclerListIngredients(listIngredient: ArrayList<Ingredients>) {
        binding.rvIngredients.layoutManager = LinearLayoutManager(this)
        val listiewRecipeAdapter = ListIngredientsAdapter(listIngredient)
        binding.rvIngredients.adapter = listiewRecipeAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater: MenuInflater = menuInflater
        menuInflater.inflate(R.menu.fav_and_share_menu, menu)
        menuInflater.inflate(R.menu.search_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.action_favorite -> {
                val dbFirestore = Firebase.firestore
                val user = FirebaseAuth.getInstance().currentUser
                val idRecipe = intent.getIntExtra("id_recipe",0)
                val docData = hashMapOf(
                    "id" to idRecipe
                )

                dbFirestore.collection("users")
                    .document(user!!.email.toString())
                    .collection("favorites")
                    .document(idRecipe.toString()).set(docData)
                    .addOnSuccessListener { Log.d(TAG, "Data berhasil disimpan ke database") }
                    .addOnFailureListener{ Log.d(TAG, "Data gagal disimpan ke database") }
                return true
            }
            R.id.action_share -> {
                val client = AsyncHttpClient()
                val idRecipe = intent.getIntExtra("id_recipe",0)
                val url = "https://api.spoonacular.com/recipes/$idRecipe/card?apiKey=$Andreas"
                client.get(url, object : AsyncHttpResponseHandler(){
                    override fun onSuccess(
                        statusCode: Int,
                        headers: Array<out Header>,
                        responseBody: ByteArray
                    ) {
                        val result = String(responseBody)
                        Log.d(TAG, result)
                        try {
                            val responseObject = JSONObject(result)
                            val urlImageShare = responseObject.getString("url")

                            val sharingIntent = Intent(Intent.ACTION_SEND)
                            sharingIntent.type = "text/plain"

                            sharingIntent.putExtra(Intent.EXTRA_TEXT, urlImageShare)
                            startActivity(Intent.createChooser(sharingIntent, "Sharing Option"))
                        } catch (e:Exception){
                            Toast.makeText(this@DetailRecipeActivity, e.message, Toast.LENGTH_SHORT).show()
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
                        Toast.makeText(this@DetailRecipeActivity, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                })
                return true
            }
        }
        return false
    }
}