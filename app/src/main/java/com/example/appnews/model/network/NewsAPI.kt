package com.example.appnews.model.network

import com.example.appnews.model.NewsResponse
import com.example.appnews.util.Constants.API_KEY
import com.example.appnews.util.Constants.BR
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {
    @GET("/api/1/news")
    suspend fun getBreakingNews(
        @Query("apiKey")
        apiKey:String = API_KEY,
        @Query("country")
        country:String = BR,
        @Query("image")
        image: String = "1"

    ) : Response<NewsResponse>

    @GET("/api/1/news")
    suspend fun searchNews(
        @Query("apiKey")
        apiKey:String = API_KEY,
        @Query("country")
        country: String = BR,
        @Query("q")
        searchQuery: String,
        @Query("image")
        image: String = "1"
    ): Response<NewsResponse>

    @GET("/api/1/news")
    suspend fun categoriesNews(
        @Query("apiKey")
        apiKey: String = API_KEY,
        @Query("country")
        country: String = BR,
        @Query("category")
        category:String = "world",
        @Query("image")
        image: String = "1"
    ): Response<NewsResponse>

}