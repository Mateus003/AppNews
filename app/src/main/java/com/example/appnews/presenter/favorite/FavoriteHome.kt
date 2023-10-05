package com.example.appnews.presenter.favorite

import androidx.lifecycle.LiveData
import com.example.appnews.model.Article

interface FavoriteHome {
    interface Presenter{
        fun onSuccess(articles: List<Article>)
    }
}