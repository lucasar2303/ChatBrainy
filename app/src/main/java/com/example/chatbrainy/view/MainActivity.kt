package com.example.chatbrainy.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import com.example.chatbrainy.ApiModule
import com.example.chatbrainy.ApiService
import com.example.chatbrainy.databinding.ActivityMainBinding
import com.example.chatbrainy.model.ApiResponse
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        fun rawJSON(text: String) {

            // Create Retrofit
            val retrofit = ApiModule().getRetrofit()

            // Create Service
            val service = retrofit?.create(ApiService::class.java)

            // Create JSON using JSONObject
            val jsonObject = JSONObject()
            jsonObject.put("model", "text-davinci-003")
            jsonObject.put("prompt", text)
            jsonObject.put("max_tokens", 250)
            jsonObject.put("temperature", 0.6)

            // Convert JSONObject to String
            val jsonObjectString = jsonObject.toString()

            // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
            val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

            CoroutineScope(Dispatchers.IO).launch {
                // Do the POST request and get response
                val response = service?.createEmployee(requestBody)

                withContext(Dispatchers.Main) {
                    if (response != null) {
                        if (response.isSuccessful) {

                            // Convert raw JSON to object using GSON library
                            val apiResponse = Gson().fromJson(response?.body()?.string(), ApiResponse::class.java)
                            Log.d("Pretty Printed JSON :", apiResponse.choices?.get(0)?.text.toString())
                            binding.response.setText(apiResponse.choices?.get(0)?.text.toString())

                        } else {
                            Log.e("RETROFIT_ERROR", response?.code().toString())
                        }
                    }

                }
            }
        }

        binding.btnSend.setOnClickListener{
            var textSend = binding.etMessage.text.toString()
            binding.etMessage.setText("")
            binding.message.setText(textSend)
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            rawJSON(textSend)

        }


    }

}