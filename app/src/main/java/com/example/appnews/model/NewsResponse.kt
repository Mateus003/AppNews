package com.example.appnews.model

data class NewsResponse(
    val nextPage: Long,
    val results: List<Article>,
    val status: String,
    val totalResults: Int
)