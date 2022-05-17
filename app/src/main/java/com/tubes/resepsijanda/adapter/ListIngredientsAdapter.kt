package com.tubes.resepsijanda.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tubes.resepsijanda.databinding.ItemIngredientsBinding
import com.tubes.resepsijanda.entity.Ingredients

class ListIngredientsAdapter(private val listIngredients: ArrayList<Ingredients>) : RecyclerView.Adapter<ListIngredientsAdapter.ListViewHolder>() {
    companion object{
        private val TAG = ListIngredientsAdapter::class.java.simpleName
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemIngredientsBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listIngredients[position])
    }

    override fun getItemCount(): Int = listIngredients.size
    inner class ListViewHolder(private val binding: ItemIngredientsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(ingredients: Ingredients) {
            with(binding){
                Glide.with(itemView.context)
                    .load(ingredients.image)
                    .apply(RequestOptions().override(100,100))
                    .into(imgIngredient)

                tvNameIngredient.text = ingredients.name
                tvAmount.text = ingredients.amount.toString()
                tvUnit.text = ingredients.unit
            }
        }

    }
}