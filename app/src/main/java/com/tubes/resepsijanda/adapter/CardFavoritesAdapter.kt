package com.tubes.resepsijanda.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tubes.resepsijanda.databinding.ItemCardFavoriteBinding
import com.tubes.resepsijanda.entity.Favorite
import com.tubes.resepsijanda.ui.recipe.DetailRecipeActivity

class CardFavoritesAdapter(private val listFavorite: ArrayList<Favorite>) : RecyclerView.Adapter<CardFavoritesAdapter.CardViewViewHolder>() {
    companion object{
        private val TAG = CardFavoritesAdapter::class.java.simpleName
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CardViewViewHolder {
        val binding = ItemCardFavoriteBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return CardViewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewViewHolder, position: Int) {
        holder.bind(listFavorite[position])
    }

    override fun getItemCount(): Int = listFavorite.size

    inner class CardViewViewHolder(private val binding:ItemCardFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorite) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(favorite.photo)
                    .apply(RequestOptions().override(250, 150))
                    .into(imgFavoriteRecipe)

                tvFavoriteRecipe.text = favorite.name
                val id = favorite.id

                btnViewFavorite.setOnClickListener{
                    Log.d(TAG, "Choose recipe ${favorite.name}")
                    val intent = Intent(itemView.context, DetailRecipeActivity::class.java)
                    intent.putExtra("id_recipe", id)
                    itemView.context.startActivity(intent)
                }

            }
        }

    }
}