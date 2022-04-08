package com.tubes.resepsijanda.ui.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.tubes.resepsijanda.R
import com.tubes.resepsijanda.adapter.GridDiscoverAdapter
import com.tubes.resepsijanda.entity.Recipe
import com.tubes.resepsijanda.databinding.FragmentDiscoverBinding

class DiscoverFragment : Fragment() {
    private val list = ArrayList<Recipe>()
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

//        val textView: TextView = binding.textDiscover
//        dashboardViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        binding.rvDiscover.setHasFixedSize(true)
        list.addAll(getListRecipe())
        showDiscoverGrid()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getListRecipe():ArrayList<Recipe>{
        val dataCategory = resources.getStringArray(R.array.category_recipe)
        val dataPhoto = resources.getStringArray(R.array.photo_recipe)

        val listRecipe = ArrayList<Recipe>()
        for (position in dataCategory.indices){
            val recipe = Recipe(
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