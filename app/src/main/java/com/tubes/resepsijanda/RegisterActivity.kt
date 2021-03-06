package com.tubes.resepsijanda

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tubes.resepsijanda.databinding.ActivityLoginBinding
import com.tubes.resepsijanda.databinding.ActivityRegisterBinding
import com.tubes.resepsijanda.ui.myrecipes.MyRecipesFragment

class RegisterActivity : AppCompatActivity(), View.OnClickListener {

//    // ViewBinding
//    private lateinit var binding: ActivityRegisterBinding

    // ActionBar
    private lateinit var actionBar: ActionBar

    // ProgressDialog
    private lateinit var progressDialog: ProgressDialog

    // FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    private var fullname = ""
    private var email = ""
    private var password = ""

    // Form text
    private lateinit var registerFullName : EditText
    private lateinit var registerEmail : EditText
    private lateinit var registerPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // configure actionbar and enable back button
        actionBar = supportActionBar!!
        actionBar.title = "Register"
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.setDisplayShowHomeEnabled(true)

        // configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Creating Account...")
        progressDialog.setCanceledOnTouchOutside(false)

        // init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        registerFullName = findViewById(R.id.register_full_name)
        registerEmail = findViewById(R.id.register_email)
        registerPassword = findViewById(R.id.register_password)

        val btnRegister: Button = findViewById(R.id.btn_register)
        btnRegister.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_register -> {
                validateData()
            }
        }
    }

    private fun validateData() {
        // get data
//        email = binding.registerEmail.text.toString().trim()
//        password = binding.registerPassword.text.toString().trim()
        email = registerEmail.getText().toString().trim()
        password = registerPassword.getText().toString().trim()

        // validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // invalid email format
//            binding.registerEmail.error = "Invalid email format."
            registerEmail.setError("Invalid email format.")
        } else if (TextUtils.isEmpty(password)) {
            // no password entered
//            binding.registerPassword.error = "Please enter your password."
            registerPassword.setError("Please enter your password.")
        } else if (password.length < 6) {
            // password length is less than 6
//            binding.registerPassword.error = "Password must be at least 6 characters long."
            registerPassword.setError("Password must be at least 6 characters long.")
        } else {
            firebaseRegister()
        }
    }

    private fun firebaseRegister() {
        // show progress
        progressDialog.show()

        // create account
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                // register success
                progressDialog.dismiss()

                // get current user
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Account created with email $email", Toast.LENGTH_SHORT).show()

                // insert data to firestore db
                insertData()

                // open main activity
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener { e->
                // register failed
                progressDialog.dismiss()
                Toast.makeText(this, "Register Failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun insertData() {
        val dbFirestore = Firebase.firestore

        fullname = registerFullName.getText().toString().trim()
        email = registerEmail.getText().toString().trim()

        // Create a new user with full name and email
        val user = hashMapOf(
            "name" to fullname,
            "email" to email
        )

        // Add a new document with a generated ID
        dbFirestore.collection("users")
            .document(email).set(user)
            .addOnSuccessListener {
                Log.d("Success","DocumentSnapshot added with email: $email")
            }
            .addOnFailureListener { e ->
                Log.w("Error adding document", e)
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}