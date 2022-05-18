package com.tubes.resepsijanda.ui.myrecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.tubes.resepsijanda.R
import com.tubes.resepsijanda.adapter.CardFavoritesAdapter
import com.tubes.resepsijanda.databinding.FragmentMyrecipesBinding
import com.tubes.resepsijanda.entity.Category
import com.tubes.resepsijanda.entity.Favorite

class MyRecipesFragment : Fragment() {
    private val list = ArrayList<Favorite>()
    private var _binding: FragmentMyrecipesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //init firebase auth
    val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        checkUser()

//        binding.btnLogout.setOnClickListener {
//            firebaseAuth.signOut()
//            checkUser()
//        }
        val notificationsViewModel =
            ViewModelProvider(this).get(MyRecipesViewModel::class.java)

        _binding = FragmentMyrecipesBinding.inflate(inflater, container, false)
        val root: View = binding.root

////      val textView: TextView = binding.textMyrecipes
//        notificationsViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        binding.rvMyRecipes.setHasFixedSize(true)
        list.addAll(getListFavorite())
        showFavoriteRecipe()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    private fun checkUser() {
//        // check user is logged in or not
//        val firebaseUser = firebaseAuth.currentUser
//        if (firebaseUser != null) {
//            // user not null, user is logged in, get user info
//            val email = firebaseUser.email
//            // set to text view
//            binding.textView.text = email
//        }else {
//            // user is null, user is not logged in
//            startActivity(Intent(getActivity(), LoginActivity::class.java))
//        }
//
//    }

    fun getListFavorite():ArrayList<Favorite>{
        val dataName = resources.getStringArray(R.array.category_recipe)
        val dataPhoto = resources.getStringArray(R.array.image_category_recipe)
        val dataID = 0

        val listFavorite = ArrayList<Favorite>()
        for (position in dataName.indices){
            val favorite = Favorite(
                dataID,
                dataName[position],
                dataPhoto[position]
            )
            listFavorite.add(favorite)
        }
        return listFavorite
    }

    private fun showFavoriteRecipe(){
        binding.rvMyRecipes.layoutManager = GridLayoutManager(context, 1)
        val cardMyRecipesAdapter = CardFavoritesAdapter(list)
        binding.rvMyRecipes.adapter = cardMyRecipesAdapter
    }
}