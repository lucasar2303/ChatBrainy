package com.example.chatbrainy.view

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chatbrainy.databinding.ActivityRecoveryPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class RecoveryPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecoveryPasswordBinding
    private var sendSup = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecoveryPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener{
            finish()
        }

        binding.btnSend.setOnClickListener{
            var email: String = binding.etEmail.text.toString()

        }

        binding.btnSend.setOnClickListener {
            val email = binding.etEmail.text.toString()
            if (email != "") {
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.btnSend.isEnabled = false
                    if (!sendSup) {
                        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(applicationContext, "Enviado", Toast.LENGTH_SHORT).show()
                                    binding.tvText.setText("Enviamos uma mensagem para o seu email com os próximos passos para recuperar sua senha!")
                                    binding.tvText.setTextSize(20F)
                                    binding.etEmail.visibility = View.GONE
                                    binding.btnSend.text = "Voltar"
                                    binding.tvError.setVisibility(View.GONE)
                                    sendSup = true
                                    binding.btnSend.isEnabled = true

                                } else {
                                    binding.btnSend.isEnabled = true
                                    var erro: String
                                    try {
                                        throw task.exception!!
                                    } catch (e: Exception) {
                                        binding.tvError.setVisibility(View.VISIBLE)
                                        binding.tvError.setText("Email não cadastrado")
                                    }
                                }
                            }
                    }else{
                        finish()
                    }

                } else {
                    binding.tvError.setVisibility(View.VISIBLE)
                    binding.tvError.setText("Email inválido")
                }
            } else {
                binding.tvError.setVisibility(View.VISIBLE)
                binding.tvError.setText("Digite seu email")
            }
        }
    }
}