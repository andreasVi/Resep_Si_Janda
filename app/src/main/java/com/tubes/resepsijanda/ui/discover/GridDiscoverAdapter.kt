package com.tubes.resepsijanda.ui.discover

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tubes.resepsijanda.Recipe
import com.tubes.resepsijanda.databinding.FragmentDiscoverBinding

class GridDiscoverAdapter (private val listRecipeCategory: ArrayList<Recipe>) : RecyclerView.Adapter<GridDiscoverAdapter.GridViewHolder>(){

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): GridDiscoverAdapter.GridViewHolder {
        val binding = FragmentDiscoverBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup, false)
        return GridViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GridDiscoverAdapter.GridViewHolder, position: Int) {
        holder.bind(listRecipeCategory[position])
    }

    override fun getItemCount(): Int = listRecipeCategory.size
    inner class GridViewHolder(private val binding: FragmentDiscoverBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(recipe: Recipe){
            with(binding){
                Glide.with(itemView.context)
                    .load(recipe.photo)
                    .apply(RequestOptions().override(550,550))
                    .into(imgDiscoverPhoto)
            }
        }
    }
}