package com.example.chatbrainy


import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @POST("completions")
    suspend fun createEmployee(@Body requestBody: RequestBody): Response<ResponseBody>
}