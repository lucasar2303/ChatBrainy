package com.example.chatbrainy.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerMain
        val layoutManager = LinearLayoutManager(this)
        layoutManager.isSmoothScrollbarEnabled = true
        recyclerView?.layoutManager = layoutManager

        binding.btnSend.setOnClickListener{
            var textSend = binding.etMessage.text.toString()

            if (textSend.length<=3){
                Toast.makeText(applicationContext, "Digite uma frase", Toast.LENGTH_SHORT).show()
            }else{
                binding.etMessage.setText("")

                var chatMessage = Chat()
                chatMessage.response = false
                chatMessage.answer = textSend

                items.add(chatMessage)

                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                callRecycler()
                lastItem = items.size
                recyclerView?.smoothScrollToPosition(lastItem!!)

                rawJSON(textSend)
            }

        }


    }

    fun rawJSON(text: String) {

        binding.progressBar2.visibility = View.VISIBLE
        binding.btnSend.visibility = View.GONE

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
                        var chatResponse = Chat()
                        chatResponse.response = true
                        chatResponse.apiResponse = apiResponse

                        items.add(chatResponse)
                        callRecycler()

                        lastItem = items.size
                        recyclerView?.smoothScrollToPosition(lastItem!!)

                        binding.progressBar2.visibility = View.GONE
                        binding.btnSend.visibility = View.VISIBLE

                    } else {
                        Log.e("RETROFIT_ERROR", response?.code().toString())

                        var chatMessage = Chat()
                        chatMessage.response = false
                        chatMessage.answer = "Erro ao encontrar resposta, se continuar a ter essa mensagem, entre em contato com o desenvolvedor"

                        items.add(chatMessage)
                        callRecycler()

                        binding.progressBar2.visibility = View.GONE
                        binding.btnSend.visibility = View.VISIBLE

                    }
                }

            }
        }
    }

    fun callRecycler(){
        adapter = ResponseMainAdapter(items)
        recyclerView?.adapter = adapter
    }


}