package com.example.api

import com.example.model.NeoxrResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface MelofyApi {
    @GET("https://api.neoxr.eu/api/spotify-search")
    suspend fun searchSpotify(
        @Query("q") query: String,
        @Query("apikey") apiKey: String = "VinzKeyNeoxr0021"
    ): NeoxrResponse

    @GET("https://api.botcahx.eu.org/api/download/spotify2")
    suspend fun downloadSpotify(
        @Query("url") url: String,
        @Query("apikey") apiKey: String = "VinzKey0110"
    ): NeoxrResponse
    
    @retrofit2.http.POST("https://generativelanguage.googleapis.com/v1beta/models/gemini-3.1-flash-lite:generateContent")
    suspend fun generateContent(
        @retrofit2.http.Query("key") key: String,
        @retrofit2.http.Body request: Map<String, @JvmSuppressWildcards Any>
    ): retrofit2.Response<Any>
    
    @retrofit2.http.GET
    suspend fun pingService(@retrofit2.http.Url url: String): retrofit2.Response<Any>
}
