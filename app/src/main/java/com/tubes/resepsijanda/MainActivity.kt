package com.tubes.resepsijanda

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.tubes.resepsijanda.adapter.ListRecipesAdapter
import com.tubes.resepsijanda.databinding.ActivityListRecipeBinding
import com.tubes.resepsijanda.databinding.ActivityMainBinding
import com.tubes.resepsijanda.entity.Recipe
import com.tubes.resepsijanda.ui.home.HomeFragment
import com.tubes.resepsijanda.ui.recipe.ListRecipe
import com.tubes.resepsijanda.util.constant
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var bindingsearch: ActivityListRecipeBinding
    companion object {
        private val TAG = MainActivity::class.java.simpleName

    }

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
                val intent = Intent(this@MainActivity, ListRecipe::class.java)
                intent.putExtra("search_recipe", query)
                Log.d(TAG, query)
                this@MainActivity.startActivity(intent)
                return true
            }

            // Gunakan method ini untuk merespon tiap perubahan huruf pada searchView
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

        })
        return true
    }

}