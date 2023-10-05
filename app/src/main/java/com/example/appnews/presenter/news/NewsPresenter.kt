package com.example.appnews.presenter.news

import android.widget.TextView
import com.example.appnews.model.NewsResponse
import com.example.appnews.model.data.NewsDataSource
import com.example.appnews.presenter.ViewHome

class NewsPresenter(val view: ViewHome.View,
    private val dataSource: NewsDataSource): NewsHome.Presenter {
    override fun requestAll() {
        this.view.showProgressBar()
        this.dataSource.getBreakingNews(this)

    }
     fun search(term: String, textNotFound: TextView) {
        this.view.showProgressBar()
        dataSource.getSearchBreakingNews(this, term, textNotFound)
    }

    fun category(category:String, textCategory: TextView, categoryText: String){
        this.view.showProgressBar()
        textCategory.text = categoryText
        dataSource.getCategoryArticle(this, category)
    }

    override fun onSuccess(newsResponse: NewsResponse) {
        view.showArticle(newsResponse.results)
    }

    override fun onError(message: String) {
        this.view.showFailure(message)
    }



    override fun onComplete() {
        this.view.hideProgressBar()
    }


}