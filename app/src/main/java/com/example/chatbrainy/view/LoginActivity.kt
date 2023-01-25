package com.example.chatbrainy.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.chatbrainy.R
import com.example.chatbrainy.databinding.ActivityLoginBinding
import com.example.chatbrainy.databinding.ActivitySplashBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var messages : Array<String> = resources.getStringArray(R.array.messages)

        binding.btnSignup.setOnClickListener{
            startActivity(Intent(this, SignupActivity::class.java))
        }

        binding.btnLogin.setOnClickListener{
            val email: String = binding.etEmail.text.toString()
            val password: String = binding.etPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()){
                binding.tvError.setText(messages[0])
            }else{
                authUser()
            }
        }
    }
    fun authUser(){
        val email: String = binding.etEmail.text.toString()
        val password: String = binding.etPassword.text.toString()
        var messages : Array<String> = resources.getStringArray(R.array.messages)

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener{task->
            if (task.isSuccessful){
                binding.progressBar.visibility = View.VISIBLE
                binding.tvError.text = ""
                startActivity(Intent(this, MainActivity::class.java))
                finish()

            }else{
                var error:String? = null
                try {
                    throw task.exception!!
                }catch (e: java.lang.Exception){
                    error = messages[8]
                }
                binding.tvError.text = error
            }

        }
    }

    override fun onStart() {
        super.onStart()
        val activeUser : FirebaseUser? = FirebaseAuth.getInstance().currentUser

        if (activeUser != null){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}