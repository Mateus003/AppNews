package com.example.appnews.model.data

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.appnews.model.Article
import com.example.appnews.model.local.db.ArticleDatabase


class NewsRepository(private val db: ArticleDatabase) {
    suspend fun updateInsert(article: Article) = db.getArticleDao().updateInsert(article)

    suspend fun getAllArticles(): List<Article> = db.getArticleDao().getAll()

    suspend fun delete(article: Article) = db.getArticleDao().deleteArticle(article)





}