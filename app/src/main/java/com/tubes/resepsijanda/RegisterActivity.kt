package com.tubes.resepsijanda

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import com.tubes.resepsijanda.databinding.ActivityLoginBinding
import com.tubes.resepsijanda.databinding.ActivityRegisterBinding
import com.tubes.resepsijanda.ui.myrecipes.MyRecipesFragment

class RegisterActivity : AppCompatActivity() {

    // ViewBinding
    private lateinit var binding: ActivityRegisterBinding

    // ActionBar
    private lateinit var actionBar: ActionBar

    // ProgressDialog
    private lateinit var progressDialog: ProgressDialog

    // FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
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

        // handle click, begin register
        binding.btnRegister.setOnClickListener {
            //validate data
            validateData()
        }
    }

    private fun validateData() {
        // get data
        email = binding.registerEmail.text.toString().trim()
        password = binding.registerPassword.text.toString().trim()

        // validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // invalid email format
            binding.registerEmail.error = "Invalid email format."
        } else if (TextUtils.isEmpty(password)) {
            // no password entered
            binding.registerPassword.error = "Please enter your password."
        } else if (password.length < 6) {
            // password length is less than 6
            binding.registerPassword.error = "Password must be at least 6 characters long."
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

                // open my recipe (profile)
                startActivity(Intent(this, MyRecipesFragment::class.java))
                finish()
            }
            .addOnFailureListener { e->
                // register failed
                progressDialog.dismiss()
                Toast.makeText(this, "Register Failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}