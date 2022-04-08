package com.tubes.resepsijanda.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tubes.resepsijanda.entity.Recipe
import com.tubes.resepsijanda.databinding.ItemGridDiscoverBinding

class GridDiscoverAdapter (private val listRecipeCategory: ArrayList<Recipe>) : RecyclerView.Adapter<GridDiscoverAdapter.GridViewHolder>(){

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): GridViewHolder {
        val binding = ItemGridDiscoverBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup, false)
        return GridViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        holder.bind(listRecipeCategory[position])
    }

    override fun getItemCount(): Int = listRecipeCategory.size

    inner class GridViewHolder(private val binding: ItemGridDiscoverBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(recipe: Recipe){
            with(binding){
                Glide.with(itemView.context)
                    .load(recipe.photo)
                    .apply(RequestOptions().override(250,250))
                    .into(imgDiscoverPhoto)

                tvDiscover.text = recipe.category
            }
        }
    }
}