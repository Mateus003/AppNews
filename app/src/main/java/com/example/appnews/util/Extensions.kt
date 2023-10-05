package com.example.appnews.util

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.appnews.R
import com.example.appnews.util.Constants.TITLE
import com.example.appnews.view.adapter.MainAdapter

fun Fragment.initToolbar(toolbar: Toolbar, titleTxt: String) {
    (activity as AppCompatActivity).setSupportActionBar(toolbar)
    (activity as AppCompatActivity).title = ""
    (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    toolbar.setNavigationOnClickListener {
        if (titleTxt == "Nóticias"){
            findNavController().navigate(R.id.action_articleFragment_to_home)
    }else if(titleTxt == "Explorar"){
            findNavController().navigate(R.id.action_articleFragment_to_explore)
        }else{
            findNavController().navigate(R.id.action_articleFavoriteFragment_to_favorite)
        }
    }
}


 fun articleCLick(adapter: MainAdapter, context: Context, fragment: Fragment, idPage: Int, titleTxt:String) {
    adapter.setOnClickListener { article ->
        if (article.link != null) {
            val bundle = Bundle().apply {
                putSerializable(Constants.ARTICLE, article)
                putString(TITLE, titleTxt)
            }
            fragment.findNavController().navigate(idPage, bundle)
        } else {
            Toast.makeText(context, "O link do artigo está indisponível", Toast.LENGTH_LONG).show()
        }
    }

}









fun buttonCloseSearch(buttonClose: View?, searchView: androidx.appcompat.widget.SearchView,
                      imageView: ImageView, visibility: Boolean){
    buttonClose?.setOnClickListener {
        searchView.setQuery("", false)
        searchView.clearFocus()
        searchView.onActionViewCollapsed()
        imageView.isVisible = visibility

    }
}
