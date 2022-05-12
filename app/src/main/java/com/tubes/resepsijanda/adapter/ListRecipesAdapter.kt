package com.tubes.resepsijanda.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tubes.resepsijanda.databinding.ItemCardRecipeBinding
import com.tubes.resepsijanda.entity.Recipe
import com.tubes.resepsijanda.ui.recipe.DetailRecipeActivity

class ListRecipesAdapter(private val listRecipes: ArrayList<Recipe>) : RecyclerView.Adapter<ListRecipesAdapter.CardViewViewHolder>() {
    companion object{
        private val TAG = ListRecipesAdapter::class.java.simpleName
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CardViewViewHolder {
        val binding = ItemCardRecipeBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return CardViewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewViewHolder, position: Int) {
        holder.bind(listRecipes[position])
    }

    override fun getItemCount(): Int = listRecipes.size
    inner class CardViewViewHolder(private val binding: ItemCardRecipeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe){
            with(binding){
                Glide.with(itemView.context)
                    .load(recipe.photo)
                    .apply(RequestOptions().override(250, 200))
                    .into(imgFood)

                tvTitle.text = recipe.name
                btnRecipe.setOnClickListener{
                    Log.d(TAG, "Choose recipe ${recipe.name}")
                    val intent = Intent(itemView.context, DetailRecipeActivity::class.java)
                    intent.putExtra("image_url", recipe.photo)
                    intent.putExtra("name_recipe", recipe.name)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}