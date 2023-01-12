package com.example.chatbrainy

//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.converter.gson.GsonConverterFactory

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.sql.Time
import java.time.Duration
import java.util.concurrent.TimeUnit


public class ApiModule {

    private var sRetrofit: Retrofit? = null
    private val URL = "https://api.openai.com/v1/"

    private val sOkHttpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.MINUTES)
        .addInterceptor(
            Interceptor { chain ->
                val request: Request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer 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")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Connection", "close")
                    .build()
                chain.proceed(request)
            }).build()

    fun getRetrofit(): Retrofit? {
        if (sRetrofit == null) {
            sRetrofit = Retrofit.Builder()
                .client(sOkHttpClient)
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return sRetrofit
    }

    }














//
//    fun client() : OkHttpClient =
//        OkHttpClient.Builder()
//            .connectTimeout(10, TimeUnit.SECONDS)
//            .readTimeout(10, TimeUnit.SECONDS)
//            .addInterceptor(HttpLoggingInterceptor().apply {
//                level = HttpLoggingInterceptor.Level.BODY
//            })
//            .build()
//
//    fun gson(): Gson = GsonBuilder().create()
//
//    fun retrofit() : Retrofit =
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .client(client())
//            .addConverterFactory(GsonConverterFactory.create(gson()))
//            .build()
//
//    fun apiService():ApiService=
//        retrofit().create(ApiService::class.java)

