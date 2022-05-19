
package com.tubes.resepsijanda.ui

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tubes.resepsijanda.R
import com.tubes.resepsijanda.adapter.SearchAdapter
import com.tubes.resepsijanda.entity.Recipe

class SearchActivity : AppCompatActivity() {

    private var listRecipe: ArrayList<Recipe> = ArrayList()

    private lateinit var rvSearch: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        rvSearch = findViewById(R.id.rv_search)
        rvSearch.setHasFixedSize(true)
        supportActionBar?.hide()
        getData()
        //showRecyclerList()
    }

    private fun getData() {
        listRecipe.clear()
        val listFilmtemp = intent.getSerializableExtra( "listFilm" )
        listRecipe = listFilmtemp as ArrayList<Recipe>
        Log.e("List size ", "${listRecipe.size}")
        rvSearch.adapter?.notifyDataSetChanged()
    }

    /*private fun showRecyclerList() {
        val SearchAdapter = SearchAdapter(applicationContext, listRecipe)
        rvSearch.adapter = SearchAdapter
        rvSearch.layoutManager = LinearLayoutManager(this)

    }*/
}
