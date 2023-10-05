package com.example.appnews.presenter.explore

import android.widget.TextView
import com.example.appnews.model.Article
import com.example.appnews.model.NewsResponse

interface ExploreHome {

    interface Presenter{
        fun category(category:String, textCategory: TextView, categoryText: String)
        fun showFailure(message:String)
        fun search(term: String, textNotFound: TextView)

        fun onSuccess(newsResponse: NewsResponse)
        fun onError(message: String)
        fun onComplete()


    }


}