package com.tubes.resepsijanda.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tubes.resepsijanda.entity.Category
import com.tubes.resepsijanda.databinding.ItemGridHomeBinding
import com.tubes.resepsijanda.ui.recipe.DetailRecipeActivity

class GridHomeAdapter(private val listCategory: ArrayList<Category>) : RecyclerView.Adapter<GridHomeAdapter.GridViewHolder>() {
    companion object{
        private val TAG = GridHomeAdapter::class.java.simpleName
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): GridViewHolder {
        val binding = ItemGridHomeBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return GridViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        holder.bind(listCategory[position])
    }

    override fun getItemCount(): Int = listCategory.size

    inner class GridViewHolder(private val binding: ItemGridHomeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(category.photo)
                    .apply(RequestOptions().override(400, 400))
                    .into(imgHomePhoto)

                tvHome.text = category.name
                val id = category.id
                imgHomePhoto.setOnClickListener{
                    Log.d(TAG, id.toString())
                    val intent = Intent(itemView.context, DetailRecipeActivity::class.java)
                    intent.putExtra("id_recipe", id)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}