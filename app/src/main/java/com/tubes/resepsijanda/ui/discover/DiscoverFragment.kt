package com.tubes.resepsijanda.ui.discover

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.tubes.resepsijanda.R
import com.tubes.resepsijanda.adapter.GridDiscoverAdapter
import com.tubes.resepsijanda.entity.Discover
import com.tubes.resepsijanda.databinding.FragmentDiscoverBinding
import com.tubes.resepsijanda.databinding.ItemGridDiscoverBinding
import com.tubes.resepsijanda.ui.recipe.ListRecipe

class DiscoverFragment : Fragment() {
    private val list = ArrayList<Discover>()
    private var _binding: FragmentDiscoverBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DiscoverViewModel::class.java)

        _binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.rvDiscover.setHasFixedSize(true)
        list.addAll(getListRecipe())
        showDiscoverGrid()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getListRecipe():ArrayList<Discover>{
        val dataCategory = resources.getStringArray(R.array.category_recipe_diet)
        val dataPhoto = resources.getStringArray(R.array.image_recipe_diet)

        val listRecipe = ArrayList<Discover>()
        for (position in dataCategory.indices){
            val recipe = Discover(
                dataCategory[position],
                dataPhoto[position]
            )
            listRecipe.add(recipe)
        }
        return listRecipe
    }

    private fun showDiscoverGrid(){
        binding.rvDiscover.layoutManager = GridLayoutManager(context, 2)
        val gridDiscoverAdapter = GridDiscoverAdapter(list)
        binding.rvDiscover.adapter = gridDiscoverAdapter
    }
}