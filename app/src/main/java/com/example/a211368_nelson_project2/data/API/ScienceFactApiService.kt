package com.example.a211368_nelson_project2.data.API

import retrofit2.http.GET
import retrofit2.http.Query

interface ScienceFactApiService {

    @GET("random.json")
    suspend fun getRandomFact(
        @Query("language") language: String = "en"
    ): ScienceFact
}