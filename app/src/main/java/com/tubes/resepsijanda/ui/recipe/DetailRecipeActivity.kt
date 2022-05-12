package com.tubes.resepsijanda.ui.recipe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.tubes.resepsijanda.R

class DetailRecipeActivity : AppCompatActivity() {
    companion object{
        private val TAG = DetailRecipeActivity::class.java.simpleName
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_recipe)

        getDetailRecipe()
    }
    private fun getDetailRecipe(){
        Log.d(TAG, "Menampilkan detail resep dari list resep yang dipilih")
        if (intent.hasExtra("image_url") && intent.hasExtra("name_recipe")){
            Log.d(TAG, "Ada data dalam intent")

            val imageRecipe = intent.getStringExtra("image_url")
            val nameRecipe = intent.getStringExtra("name_recipe")
            Log.d(TAG, imageRecipe.toString())

            setDetailRecipe(nameRecipe, imageRecipe)
        }
    }

    private fun setDetailRecipe(nameRecipe: String?, imgUrl: String?){
        Log.d(TAG, "Resep ditampilkan")

        val name: TextView = findViewById(R.id.name_recipe)
        name.text = nameRecipe

        val image: ImageView = findViewById(R.id.img_recipe)
        Glide.with(this)
            .load(imgUrl)
            .error(R.drawable.ic_launcher_background)
            .into(image)
    }
}