package com.example.appnews.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appnews.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDAO {
    @Query("SELECT * FROM articles")
    fun getAll(): List<Article>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
      fun  updateInsert(article: Article): Long


    @Delete
     fun deleteArticle(article: Article)




}