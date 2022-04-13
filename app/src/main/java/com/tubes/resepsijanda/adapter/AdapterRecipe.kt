package com.tubes.resepsijanda.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tubes.resepsijanda.data.ResultRecipes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tubes.resepsijanda.databinding.ItemRecipesBinding

class AdapterRecipe (private val listRecipe: ArrayList<ResultRecipes>?) : RecyclerView.Adapter<AdapterRecipe.RecipeViewHolder>(){
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): AdapterRecipe.RecipeViewHolder {
        val binding = ItemRecipesBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup, false)

        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterRecipe.RecipeViewHolder, position: Int) {
        holder.bind(listRecipe?.get(position)!!)
    }

    override fun getItemCount(): Int = listRecipe?.size?:0

    inner class RecipeViewHolder(private val binding: ItemRecipesBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(recipes: ResultRecipes){
            Glide.with(itemView.context)
                .load(recipes.image)
                .apply(RequestOptions().override(200,200))
                .into(binding.imgvRecipes)

            binding.tvRecipesName.text = recipes.title
        }
    }
}