package com.example.appnews.presenter.favorite

import androidx.lifecycle.LiveData
import com.example.appnews.model.Article
import com.example.appnews.model.data.NewsDataSource
import com.example.appnews.presenter.ViewHome

class FavoritePresenter(private val view:ViewHome.Favorite,private val dataSource: NewsDataSource): FavoriteHome.Presenter {
    fun saveArticle(article: Article){
        this.dataSource.saveArticle(article)
    }


    override fun onSuccess(articles: List<Article>) {
        this.view.showArticle(articles)
    }

    fun getAll(){
        dataSource.getAllArticles(this)
    }

    fun deleteArticle(article: Article){
        dataSource.deleteArticle(article)
    }


}