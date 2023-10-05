package com.example.appnews.model.data

import android.content.Context
import android.util.Log
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import com.example.appnews.model.Article
import com.example.appnews.model.local.db.ArticleDatabase
import com.example.appnews.model.network.HTTPClient
import com.example.appnews.presenter.explore.ExplorePresenter
import com.example.appnews.presenter.favorite.FavoriteHome
import com.example.appnews.presenter.news.NewsHome
import com.example.appnews.presenter.news.NewsPresenter
import com.example.appnews.util.Constants.API_KEY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class NewsDataSource(context: Context) {
    private var db = ArticleDatabase(context)
    private var newsRepository: NewsRepository = NewsRepository(db)



    fun getBreakingNews(callback: NewsHome.Presenter) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = HTTPClient.api.getBreakingNews()
                if (response.isSuccessful) {
                    response.body()?.let { NewsResponse ->
                        callback.onSuccess(NewsResponse)
                    }

                } else {
                    callback.onError(response.message())
                }
                callback.onComplete()

            } catch (e: IOException) {
                callback.onError("No internet connection")
                callback.onComplete()

            }
        }
    }

    fun getSearchBreakingNews(callback: NewsPresenter, term:String, textView: TextView){
        GlobalScope.launch(Dispatchers.Main){
           try {
           val response =  HTTPClient.api.searchNews(API_KEY, "br",term)
            if (response.isSuccessful){
                response?.body()?.let {NewsResponse->
                        callback.onSuccess(NewsResponse)
                    textView.isVisible = NewsResponse.totalResults == 0


                }
                callback.onComplete()
            }else {
                callback.onError(response.message())
                callback.onComplete()
        }

           }
           catch (e: IOException){
               callback.onError("No internet connection")
               callback.onComplete()
           }
        }
    }


    fun getSearchBreakingNewsCategory(callback: ExplorePresenter, term:String, textView: TextView){
        GlobalScope.launch(Dispatchers.Main){
            try {
                val response =  HTTPClient.api.searchNews(API_KEY, "br",term)
                if (response.isSuccessful){
                    response?.body()?.let {NewsResponse->
                        callback.onSuccess(NewsResponse)
                        textView.isVisible = NewsResponse.totalResults == 0

                    }
                    callback.onComplete()
                }else {
                    callback.onError(response.message())
                    callback.onComplete()
                }

            }
            catch (e: IOException){
                callback.onError("No internet connection")
                callback.onComplete()
            }
        }
    }


    fun getCategoryArticle(callback: NewsPresenter, category:String){
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = HTTPClient.api.categoriesNews(API_KEY, "br",category)
                if (response.isSuccessful){
                    response.body()?.let {NewsResponse->
                        callback.onSuccess(NewsResponse)
                    }

                }else {
                    callback.onError(response.message())
                }
                callback.onComplete()

            }catch (e: IOException){
                callback.onError("No internet connection")
                callback.onComplete()
            }
        }
    }



    //Configuração do banco de dados

    fun saveArticle(article: Article){
        GlobalScope.launch(Dispatchers.IO) {
            newsRepository.updateInsert(article)
        }


    }

    fun getAllArticles(callback: FavoriteHome.Presenter){
        var allArticles: List<Article>
        CoroutineScope(Dispatchers.IO).launch {
            allArticles = newsRepository.getAllArticles()
            withContext(Dispatchers.Main){
                callback.onSuccess(allArticles)

            }

        }
    }

    fun deleteArticle(article: Article?){
        GlobalScope.launch(Dispatchers.IO) {
            article?.let {
                newsRepository.delete(it)
            }
        }
    }





}
