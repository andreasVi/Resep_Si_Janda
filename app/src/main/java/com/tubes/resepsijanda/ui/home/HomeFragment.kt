package com.tubes.resepsijanda.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tubes.resepsijanda.R
import com.tubes.resepsijanda.adapter.GridHomeAdapter
import com.tubes.resepsijanda.databinding.FragmentHomeBinding
import com.tubes.resepsijanda.entity.Category

class HomeFragment : Fragment() {
    private val list = ArrayList<Category>()
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        binding.rvCategory.setHasFixedSize(true)
        list.addAll(getListCategory())
        showHomeGrid()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getListCategory():ArrayList<Category>{
        val dataName = resources.getStringArray(R.array.name_category)
        val dataPhoto = resources.getStringArray(R.array.photo_category)

        val listCategory = ArrayList<Category>()
        for (position in dataName.indices){
            val category = Category(
                dataName[position],
                dataPhoto[position]
            )
            listCategory.add(category)
        }
        return listCategory
    }

    private fun showHomeGrid(){
        binding.rvCategory.layoutManager = GridLayoutManager(context, 2)
        val gridHomeAdapter = GridHomeAdapter(list)
        binding.rvCategory.adapter = gridHomeAdapter
    }
}