package com.example.appnews.presenter

import com.example.appnews.model.Article
import com.example.appnews.model.NewsResponse

interface ViewHome {
    interface View{
        fun showProgressBar()
        fun showFailure(message:String)
        fun hideProgressBar()
        fun showArticle(articles: List<Article>)
    }


    interface Favorite{

        fun showArticle(article: List<Article>)
    }
}