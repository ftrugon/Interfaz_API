package com.example.din_api.data

import android.graphics.Region
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


object RetrofitServiceFactory {

    private const val staticUrl = "https://adaproyectoapimongo.onrender.com"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(staticUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    val retrofitService: ApiService by lazy {
        getRetrofit().create(ApiService::class.java)
    }


}