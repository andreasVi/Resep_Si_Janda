package com.tubes.resepsijanda.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tubes.resepsijanda.databinding.ItemGridMyrecipesBinding
import com.tubes.resepsijanda.entity.Favorite

class GridMyrecipesAdapter(private val listFavorite: ArrayList<Favorite>) : RecyclerView.Adapter<GridMyrecipesAdapter.GridViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): GridViewHolder {
        val binding = ItemGridMyrecipesBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return GridViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        holder.bind(listFavorite[position])
    }

    override fun getItemCount(): Int = listFavorite.size

    inner class GridViewHolder(private val binding: ItemGridMyrecipesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorite) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(favorite.photo)
                    .apply(RequestOptions().override(500, 550))
                    .into(imgMyrecipesPhoto)

                tvMyrecipes.text = favorite.favorite
            }
        }
    }
}