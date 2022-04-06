package com.tubes.resepsijanda.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tubes.resepsijanda.R
import com.tubes.resepsijanda.databinding.ItemGridCategoryBinding
import com.tubes.resepsijanda.entity.Category

class GridCategoryAdapter(private val listCategory: ArrayList<Category>) : RecyclerView.Adapter<GridCategoryAdapter.GridViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): GridViewHolder {
        val binding = ItemGridCategoryBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return GridViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        holder.bind(listCategory[position])
    }

    override fun getItemCount(): Int = listCategory.size

    inner class GridViewHolder(private val binding: ItemGridCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            Glide.with(itemView.context)
                .load(category.photo)
                .apply(RequestOptions().override(500, 550))
                .into(category_item_photo)
            }
        }
    }
}