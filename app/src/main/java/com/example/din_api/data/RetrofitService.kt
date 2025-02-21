package com.example.din_api.data

import android.graphics.Region
import com.example.din_api.data.model.GamesList
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


object RetrofitServiceFactory {

    private const val staticUrl = "http://192.168.0.105:8080"

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