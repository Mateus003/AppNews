package com.example.appnews.presenter.news

import com.example.appnews.model.NewsResponse

interface NewsHome {
    interface Presenter{
        fun requestAll()
        fun onSuccess(newsResponse: NewsResponse)
        fun onError(message: String)
        fun onComplete()
    }
}