package com.tubes.resepsijanda

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.tubes.resepsijanda.databinding.ActivityMainBinding
import com.tubes.resepsijanda.ui.discover.GridDiscoverAdapter

class MainActivity : AppCompatActivity() {
    private val list = ArrayList<Recipe>()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_discover, R.id.navigation_myrecipes
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        binding.rvDiscover.setHasFixedSize(true)
        list.addAll(getListRecipe())
        showDiscoverGrid()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search_menu).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String): Boolean {
            // Gunakan method ini ketika search selesai atau OK
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                return true
            }

            // Gunakan method ini untuk merespon tiap perubahan huruf pada searchView
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

        })
        return true
    }

    fun getListRecipe():ArrayList<Recipe>{
        val nameRecipe = resources.getStringArray(R.array.name_recipe)
        val dataCategory = resources.getStringArray(R.array.category_recipe)
        val dataRecipe = resources.getStringArray(R.array.recipe)
        val dataPhoto = resources.getStringArray(R.array.photo_recipe)

        val listRecipe = ArrayList<Recipe>()
        for (position in nameRecipe.indices){
            val recipe = Recipe(
                nameRecipe[position],
                dataCategory[position],
                dataRecipe[position],
                dataPhoto[position]
            )
            listRecipe.add(recipe)
        }
        return listRecipe
    }

    private fun showDiscoverGrid(){
        binding.rvDiscover.layoutManager = GridLayoutManager(this, 2)
        val gridDiscoverAdapter = GridDiscoverAdapter(list)
        binding.rvDiscover.adapter = gridDiscoverAdapter
    }
}