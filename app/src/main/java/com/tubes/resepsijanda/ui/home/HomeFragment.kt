package com.tubes.resepsijanda.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.tubes.resepsijanda.adapter.GridHomeAdapter
import com.tubes.resepsijanda.databinding.FragmentHomeBinding
import com.tubes.resepsijanda.entity.Recipe
import com.tubes.resepsijanda.util.constant.Companion.compSearch
import com.tubes.resepsijanda.util.constant.Companion.home
import com.tubes.resepsijanda.util.constant.Companion.key
import com.tubes.resepsijanda.util.constant.Companion.spoonacular
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class HomeFragment : Fragment() {
//    private val list = ArrayList<Category>()
    private var _binding: FragmentHomeBinding? = null
    companion object{
        private val TAG = HomeFragment::class.java.simpleName
    }

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
        getListCategory()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getListCategory(){
        val client = AsyncHttpClient()
        val listPopularity = ArrayList<Recipe>()

        val url = spoonacular + compSearch + home + key
        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                Log.d(TAG, result)

                try {
                    val responseObject = JSONObject(result)
                    val jsonArray = responseObject.getJSONArray("results")

                    for (i in 0 until jsonArray.length()){
                        val id = jsonArray.getJSONObject(i).getInt("id")
                        val title = jsonArray.getJSONObject(i).getString("title")
                        val image = jsonArray.getJSONObject(i).getString("image")

                        val recipe = Recipe(
                            id,
                            title,
                            image
                        )
                        listPopularity.add(recipe)
                    }
                    showHomeGrid(listPopularity)

                }catch (e:Exception){
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                //Jika koneksi gagal
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

//    fun getListCategory():ArrayList<Category>{
//        val dataName = resources.getStringArray(R.array.name_category)
//        val dataPhoto = resources.getStringArray(R.array.photo_category)
//
//        val listCategory = ArrayList<Category>()
//        for (position in dataName.indices){
//            val category = Category(
//                dataName[position],
//                dataPhoto[position]
//            )
//            listCategory.add(category)
//        }
//        return listCategory
//    }

    private fun showHomeGrid(listCategory: ArrayList<Recipe>){
        binding.rvCategory.layoutManager = GridLayoutManager(context, 2)
        val gridHomeAdapter = GridHomeAdapter(listCategory)
        binding.rvCategory.adapter = gridHomeAdapter
    }
}