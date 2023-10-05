package com.example.appnews.model.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.appnews.model.Article
import com.example.appnews.model.local.dao.ArticleDAO


@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class ArticleDatabase: RoomDatabase() {
    abstract fun getArticleDao():ArticleDAO

    companion object{
        private var instance: ArticleDatabase? = null

        private val Lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(Lock){
            instance ?: createdDatabase(context).also {
                instance = it
            }
        }

        private fun createdDatabase(context: Context) = Room.databaseBuilder(context.applicationContext, ArticleDatabase::class.java, "article_db.db").build()

    }
 }

