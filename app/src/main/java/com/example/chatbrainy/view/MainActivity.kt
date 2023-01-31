package com.example.chatbrainy.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatbrainy.ApiModule
import com.example.chatbrainy.ApiService
import com.example.chatbrainy.adapter.ResponseMainAdapter
import com.example.chatbrainy.databinding.ActivityMainBinding
import com.example.chatbrainy.model.ApiResponse
import com.example.chatbrainy.model.Chat
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
    val items = mutableListOf<Chat>()
    var recyclerView:RecyclerView? = null
    var adapter:ResponseMainAdapter? = null
    var lastItem:Int? = 0
    var sharedPreferences: SharedPreferences? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = applicationContext?.getSharedPreferences("pref", Context.MODE_PRIVATE)


        //Config RecyclerView
        configRecycler()

        //Adjust view keyboard open
        if (Build.VERSION.SDK_INT >= 11) {
            recyclerView!!.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
                if (bottom < oldBottom) {
                    recyclerView!!.scrollBy(0, oldBottom - bottom);
                }
            }
        }

        binding.btnSettings.setOnClickListener{
            startActivity(Intent(this, SettingsActivity::class.java))
        }


        binding.btnSend.setOnClickListener{
            var textSend = binding.etMessage.text.toString()

            //checks if at least one word of more than 3 letters was typed
            if (textSend.length<=3){
                Toast.makeText(applicationContext, "Digite uma frase", Toast.LENGTH_SHORT).show()
            }else{
                binding.etMessage.setText("")

                // Creating request item for list
                var chatMessage = Chat()
                chatMessage.response = false
                chatMessage.answer = textSend

                // Adding item to list and updating recycler
                items.add(chatMessage)
                callRecycler()

                // Hiding keyboard
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

                // Positioning to the last item added
                lastItem = items.size
                recyclerView?.smoothScrollToPosition(lastItem!!)

                // request API
                rawJSON(textSend)
            }

        }


    }

    fun rawJSON(text: String) {

        // Showing loading
        showLoading(true)

        // Create Retrofit
        val retrofit = ApiModule().getRetrofit()

        // Create Service
        val service = retrofit?.create(ApiService::class.java)

        // Create JSON using JSONObject
        val jsonObject = JSONObject()
        jsonObject.put("model", sharedPreferences!!.getString("model", "text-davinci-003"))
        jsonObject.put("prompt", text)
        jsonObject.put("max_tokens", sharedPreferences!!.getInt("max_tokens", 250))
        jsonObject.put("temperature", sharedPreferences!!.getFloat("temperature", 0.7F))

        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()

        // Create RequestBody
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

                        // Creating response item for list
                        var chatResponse = Chat()
                        chatResponse.response = true
                        chatResponse.apiResponse = apiResponse

                        // Adding item to list and updating recycler
                        items.add(chatResponse)
                        callRecycler()

                        // Positioning to the last item added
                        lastItem = items.size
                        recyclerView?.smoothScrollToPosition(lastItem!!)

                        // Showing send button
                        showLoading(false)


                    } else {
                        Log.e("RETROFIT_ERROR", response?.code().toString())

                        // Creating error item for list
                        var chatMessage = Chat()
                        chatMessage.response = false
                        chatMessage.answer = "Não foi possível completar a requisição, troque o modelo nas configurações de Chat e tente novamente. Se continuar a ter esse erro, entre em contato com o desenvolvedor."

                        // Adding item to list and updating recycler
                        items.add(chatMessage)
                        callRecycler()

                        // Showing send button
                        showLoading(false)

                    }
                }

            }
        }
    }

    fun callRecycler(){
        //updating recycler
        adapter = ResponseMainAdapter(items)
        recyclerView?.adapter = adapter
    }

    fun showLoading(aux: Boolean){
        if (aux){
            binding.progressBar2.visibility = View.VISIBLE
            binding.btnSend.visibility = View.GONE
        }else{
            binding.progressBar2.visibility = View.GONE
            binding.btnSend.visibility = View.VISIBLE
        }
    }

    fun configRecycler(){
        recyclerView = binding.recyclerMain
        val layoutManager = LinearLayoutManager(this)
        layoutManager.isSmoothScrollbarEnabled = true
        layoutManager.stackFromEnd = true
        recyclerView?.layoutManager = layoutManager
    }


}