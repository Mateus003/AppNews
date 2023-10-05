package com.example.appnews.presenter.explore

import android.widget.TextView
import com.example.appnews.model.NewsResponse
import com.example.appnews.model.data.ExploreDataSource
import com.example.appnews.presenter.ViewHome
import com.xwray.groupie.GroupieAdapter

class ExplorePresenter(private val dataSource: ExploreDataSource, private val view: ViewHome.View) : ExploreHome.Presenter{
    override fun category(category: String, textCategory: TextView, categoryText: String) {
        dataSource.getCategoryArticle(this, category)
        this.view.showProgressBar()
        textCategory.text = categoryText

    }



    override fun showFailure(message: String) {
        view.showFailure(message)
    }

    override fun search(term: String, textNotFound: TextView) {
        dataSource.getSearchBreakingNewsCategory(this, term, textNotFound)
        this.view.showProgressBar()
    }

    override fun onSuccess(newsResponse: NewsResponse) {
        this.view.showArticle(newsResponse.results)
    }

    override fun onError(message: String) {
        this.view.hideProgressBar()
    }

    override fun onComplete() {
        this.view.hideProgressBar()
    }






}