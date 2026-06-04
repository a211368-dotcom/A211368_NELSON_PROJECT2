package com.example.a211368_nelson_project2.data.API


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ScienceFactApiClient {

    private const val BASE_URL = "https://uselessfacts.jsph.pl/"

    val apiService: ScienceFactApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ScienceFactApiService::class.java)
    }
}