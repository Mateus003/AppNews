package com.example.appnews.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "articles")
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val article_id: String?,
    val content: String?,
    val description: String?,
    val image_url: String?,
    val language: String?,
    val link: String?,
    val source_id: String?,
    val title: String?,
): Serializable