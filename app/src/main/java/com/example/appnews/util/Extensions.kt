package com.example.appnews.util

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.appnews.R
import com.example.appnews.util.Constants.TITLE
import com.example.appnews.view.adapter.MainAdapter

fun Fragment.initToolbar(toolbar: Toolbar, titleTxt: String) {
    (activity as AppCompatActivity).setSupportActionBar(toolbar)
    (activity as AppCompatActivity).title = ""
    (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    toolbar.setNavigationOnClickListener {
        if (titleTxt == "Notícias"){
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
            Toast.makeText(context, fragment.getString(R.string.urlArticleOff),Toast.LENGTH_LONG).show()
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

fun btnBackTextSubmitList(imageView: ImageView, searchView: SearchView, textNotFound: TextView, returnHome: ()-> Unit){
    imageView.setOnClickListener {
        //Limpa o foco do SearchView para fechá-lo
        searchView.clearFocus()
        searchView.setQuery("", false)

        searchView.onActionViewCollapsed()

        imageView.isVisible = false

        returnHome()


        textNotFound.isVisible= false

    }
}

fun btnBackSearch(btnBackImage:ImageView, searchView: SearchView){
    btnBackImage.setOnClickListener {
        btnBackImage .isVisible = false
        searchView.onActionViewCollapsed()
        searchView.setQuery("", false)
        searchView.clearFocus()

    }

}

fun onBackPressed(fragment: Fragment, configBackPressed: ()-> Unit){
    val callback = object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            configBackPressed()
        }

    }
    fragment.requireActivity().onBackPressedDispatcher.addCallback(callback)
}
