package com.tubes.resepsijanda.ui.myrecipes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.tubes.resepsijanda.LoginActivity
import com.tubes.resepsijanda.MainActivity
import com.tubes.resepsijanda.adapter.CardFavoritesAdapter
import com.tubes.resepsijanda.databinding.FragmentMyrecipesBinding
import com.tubes.resepsijanda.entity.Favorite
import com.tubes.resepsijanda.util.constant.Companion.information
import com.tubes.resepsijanda.util.constant.Companion.key
import com.tubes.resepsijanda.util.constant.Companion.spoonacular
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MyRecipesFragment : Fragment() {
    private val list = ArrayList<Favorite>()
    private var _binding: FragmentMyrecipesBinding? = null
    companion object{
        private val TAG = MyRecipesFragment::class.java.simpleName
    }
//    private lateinit var btnLogout: Button

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //init firebase auth
    val firebaseAuth = FirebaseAuth.getInstance()
    val firebaseUser = firebaseAuth.currentUser

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMyrecipesBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val view: View = inflater!!.inflate(R.layout.fragment_myrecipes, container, false)
//        btnLogout = view.findViewById(R.id.button_logout)
////
//        btnLogout.setOnClickListener { view ->
//            Log.d("button", ": clicked")
//        }
        
//        val notificationsViewModel =
//            ViewModelProvider(this).get(MyRecipesViewModel::class.java)

        //To check if user is logged in
        checkUser()

        //Logout button
        binding.buttonLogout.setOnClickListener{
            Log.d("clicked", ": Logout Success")
            firebaseAuth.signOut()
            startActivity(Intent(getActivity(), MainActivity::class.java))
        }

////      val textView: TextView = binding.textMyrecipes
//        notificationsViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        binding.rvMyRecipes.setHasFixedSize(true)
        getFavorites()
        return root
    }

//    override fun onClick(v: View?) {
//        when (v?.id) {
//            R.id.button_logout -> {
//                firebaseAuth.signOut()
//                // checkUser()
//            }
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkUser() {
        // check user is logged in or not
        if (firebaseUser != null) {
            // user not null, user is logged in, get user info
            val email = firebaseUser.email
            Log.d("email",email!!)

            //Get data from fire store
            getDataFromDb(email)

        }else {
            // user is null, user is not logged in
            startActivity(Intent(getActivity(), LoginActivity::class.java))
        }
    }

    private fun getDataFromDb(email: String){
        val db = Firebase.firestore
        val docRef = db.collection("users").document(email)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val data = document.data
                    val image = data!!.get("image").toString()
                    val name = data!!.get("name").toString()
                    Log.d("Document data: ", " image = $image name = $name")

                    //Set username by logged in user
                    binding.userName.text = name

                    //Set user image by logged in user
                    Glide.with(this)
                        .load(image)
                        .into(binding.userImage);

                } else {
                    Log.d("Document data: ", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Document data: ", "get failed with ", exception)
            }
    }

    fun getFavorites(){
        //dummy list of id which is added to favorites
        val listFavoritesID = arrayOf(664429, 658007, 715569, 643450, 642780)
        val client = AsyncHttpClient()
        val listFavoriteRecipes = ArrayList<Favorite>()
//        Log.d("favorite count: ", "${listFavoritesID.size}")
        for (i in 0 until listFavoritesID.size){
            val url = spoonacular + listFavoritesID[i] + information + key
            client.get(url, object : AsyncHttpResponseHandler() {
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>,
                    responseBody: ByteArray
                ) {
                    //if connection success
                    val result = String(responseBody)
                    Log.d(TAG, result)
                    try {
                        val responseObject = JSONObject(result)
                        val id = responseObject.getInt("id")
                        val title = responseObject.getString("title")
                        val image = responseObject.getString("image")

                        val favorites = Favorite(
                            id,
                            title,
                            image
                        )
                        listFavoriteRecipes.add(favorites)
                        showFavoriteRecipe(listFavoriteRecipes)
                    }catch (e:Exception){
                        Toast.makeText(requireActivity(), e.message, Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(requireActivity(), errorMessage, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun showFavoriteRecipe(listFavoriteRecipes:ArrayList<Favorite>){
        binding.rvMyRecipes.layoutManager = LinearLayoutManager(requireActivity())
        val cardMyRecipesAdapter = CardFavoritesAdapter(listFavoriteRecipes)
        binding.rvMyRecipes.adapter = cardMyRecipesAdapter
    }
}
