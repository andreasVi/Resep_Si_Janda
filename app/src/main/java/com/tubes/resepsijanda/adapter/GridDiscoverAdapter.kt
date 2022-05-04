package com.tubes.resepsijanda.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tubes.resepsijanda.entity.Discover
import com.tubes.resepsijanda.databinding.ItemGridDiscoverBinding
import com.tubes.resepsijanda.ui.discover.DiscoverFragment
import com.tubes.resepsijanda.ui.recipe.ListRecipe

class GridDiscoverAdapter (private val listDiscoverCategory: ArrayList<Discover>) : RecyclerView.Adapter<GridDiscoverAdapter.GridViewHolder>(){
    companion object{
        private val TAG = GridDiscoverAdapter::class.java.simpleName
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): GridViewHolder {
        val binding = ItemGridDiscoverBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup, false)
        return GridViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        holder.bind(listDiscoverCategory[position])
    }

    override fun getItemCount(): Int = listDiscoverCategory.size

    inner class GridViewHolder(private val binding: ItemGridDiscoverBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(discover: Discover){
            with(binding){
                Glide.with(itemView.context)
                    .load(discover.photo)
                    .apply(RequestOptions().override(250,250))
                    .into(imgDiscoverPhoto)

                tvDiscover.text = discover.category
                imgDiscoverPhoto.setOnClickListener{
                    Log.d(TAG, "Discover yang dipilih: ${discover.category}")
                    val intent = Intent(itemView.context, ListRecipe::class.java)
                    intent.putExtra("name_discover", discover.category)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}