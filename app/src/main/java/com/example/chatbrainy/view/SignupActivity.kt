package com.example.chatbrainy.view

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.chatbrainy.R
import com.example.chatbrainy.databinding.ActivityLoginBinding
import com.example.chatbrainy.databinding.ActivitySignupBinding
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import io.grpc.internal.SharedResourceHolder.Resource

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var messages : Array<String> = resources.getStringArray(R.array.messages)

        binding.btnBack.setOnClickListener{
            finish()
        }

        binding.btnSignup.setOnClickListener{
            finish()
        }

        binding.btnSignupConfirm.setOnClickListener{
            var name = binding.etName.text.toString()
            var email = binding.etEmail.text.toString()
            var phone = binding.etPhone.text.toString()
            var password = binding.etPassword.text.toString()
            var passwordConfirm = binding.etConfirmPassword.text.toString()

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty() ){
                binding.tvError.text = messages[0]
            }else if (!password.equals(passwordConfirm)){
                binding.tvError.text = messages[1]
            }else if (!binding.etPhone.isDone){
                binding.tvError.text = messages[2]
            }else if (password.length<8){
                binding.tvError.text = messages[3]
            }else{
                signupUser()
            }
        }

    }

    fun signupUser(){
        var email = binding.etEmail.text.toString()
        var password = binding.etPassword.text.toString()
        var messages : Array<String> = resources.getStringArray(R.array.messages)

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener{ task ->
            if (task.isSuccessful) {

                saveDataUser()

                Toast.makeText(applicationContext, messages[7], Toast.LENGTH_SHORT).show()
                finish()
            }else{
                var error:String? = null
                try {
                    throw task.exception!!

                }catch (e: FirebaseAuthWeakPasswordException){
                    error = messages[3]
                }catch (e: FirebaseAuthUserCollisionException){
                    error = messages[4]
                }catch (e: FirebaseAuthInvalidCredentialsException){
                    error = messages[5]
                }
                catch (e: java.lang.Exception){
                    error = messages[6]
                }
                binding.tvError.text = error

            }
        }


    }

    fun saveDataUser(){
        var name = binding.etName.text.toString()
        var email = binding.etEmail.text.toString()
        var phone = binding.etPhone.text.toString()

        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val dataUser = HashMap<String,Any>()
        dataUser.put("name", name)
        dataUser.put("email", email)
        dataUser.put("phone", phone)

        userId = FirebaseAuth.getInstance().currentUser?.uid

        var docRef: DocumentReference = db.collection("Users").document(userId!!)
        docRef.set(dataUser).addOnSuccessListener( {
            Log.d("Database :", "Sucess data user create")
        }).addOnFailureListener({
            Log.d("Database :", "Error data user create" + it.toString())
        })

    }
}