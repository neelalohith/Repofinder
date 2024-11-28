package com.example.repofinder.data.Remote

import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {

    // Base URL for GitHub API
    private const val BASE_URL = "https://api.github.com/"

    val apiService: ApiService by lazy {
        val moshi = Moshi.Builder().build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ApiService::class.java)
    }
}
