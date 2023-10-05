package com.example.appnews.model.data

import android.widget.TextView
import androidx.core.view.isVisible
import com.example.appnews.model.network.HTTPClient
import com.example.appnews.presenter.explore.ExplorePresenter
import com.example.appnews.presenter.news.NewsPresenter
import com.example.appnews.util.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException

class ExploreDataSource {


    fun getSearchBreakingNewsCategory(callback: ExplorePresenter, term:String, textView: TextView){
        GlobalScope.launch(Dispatchers.Main){
            try {
                val response =  HTTPClient.api.searchNews(Constants.API_KEY, "br",term)
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


    fun getCategoryArticle(callback: ExplorePresenter, category:String="world"){
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = HTTPClient.api.categoriesNews(Constants.API_KEY, "br",category)
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
}