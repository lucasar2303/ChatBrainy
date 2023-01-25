package com.example.chatbrainy.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chatbrainy.databinding.ActivityLoginBinding
import com.example.chatbrainy.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener{
            finish()
        }

        binding.btnSignup.setOnClickListener{
            finish()
        }

    }
}