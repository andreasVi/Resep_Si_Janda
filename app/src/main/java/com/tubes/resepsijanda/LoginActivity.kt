package com.tubes.resepsijanda

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import com.tubes.resepsijanda.databinding.ActivityLoginBinding
import com.tubes.resepsijanda.ui.myrecipes.MyRecipesFragment

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    // ViewBinding
//    private lateinit var binding: ActivityLoginBinding

    // ActionBar
    private lateinit var actionBar: ActionBar

    // ProgressDialog
    private lateinit var progressDialog: ProgressDialog

    // FirebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""

    private lateinit var loginEmail: EditText
    private lateinit var loginPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_login)

        // configure actionbar
        actionBar = supportActionBar!!
        actionBar.title = "Login"

        // configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Logging In...")
        progressDialog.setCanceledOnTouchOutside(false)

        // init firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()
//        checkUser()

        loginEmail = findViewById(R.id.login_email)
        loginPassword = findViewById(R.id.login_password)

        val btnLogin: Button = findViewById(R.id.btn_login)
        btnLogin.setOnClickListener(this)

        val tvNoAccount: TextView = findViewById(R.id.tv_no_account)
        tvNoAccount.setOnClickListener(this)

//        // handle click, begin login
//        binding.btnLogin.setOnClickListener {
//            // before logging in, validate data
//            validateData()
//        }

//        // handle click, open register activity
//        binding.tvNoAccount.setOnClickListener {
//            startActivity(Intent(this, RegisterActivity::class.java))
//        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_login -> {
                validateData()
            }
            R.id.tv_no_account -> {
                startActivity(Intent(this, RegisterActivity::class.java))
            }
        }
    }

    private fun validateData() {
        // get data
//        email = binding.loginEmail.toString().trim()
//        password = binding.loginPassword.toString().trim()
        email = loginEmail.getText().toString().trim()
        password = loginPassword.getText().toString().trim()

        // validate data
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // invalid email form
            loginEmail.setError("Invalid email format.")
        } else if (TextUtils.isEmpty(password)) {
            // no password entered
            loginPassword.setError("Please enter your password.")
        } else {
            // data is validated, begin login
            firebaseLogin()
        }
    }

    private fun firebaseLogin() {
        // show progress
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                // login success
                progressDialog.dismiss()

                // get user info
                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Logged in as $email", Toast.LENGTH_SHORT).show()

                // open my recipes (profile)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener { e->
                // login failed
                progressDialog.dismiss()
                Toast.makeText(this, "Login failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

//    private fun checkUser() {
//        // if user is already logged in go to profile activity
//
//        // get current user
//        val firebaseUser = firebaseAuth.currentUser
//        if (firebaseUser != null) {
//            // user is already logged in
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
//        }
//    }
}