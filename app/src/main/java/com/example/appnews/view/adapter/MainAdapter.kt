package com.example.appnews.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.appnews.R
import com.example.appnews.model.Article


class MainAdapter : RecyclerView.Adapter<MainAdapter.ArticleViewHolder>() {
    inner class ArticleViewHolder(itemView:View): ViewHolder(itemView)



    private val diffCallback = object : DiffUtil.ItemCallback<Article>(){

        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean = oldItem.link == newItem.link

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean = oldItem.link == newItem.link

    }

    val differ = AsyncListDiffer(this, diffCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder =
        ArticleViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_main, parent,false)
        )

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {


        val article = differ.currentList[position]
        holder.itemView.apply {
                Glide.with(this).
                load(article.image_url).
                    placeholder(R.drawable.ic_news).
                    error(R.drawable.ic_news).transition(DrawableTransitionOptions.withCrossFade())
                    .into(findViewById(R.id.articleImage))



                findViewById<TextView>(R.id.txt_title).text = article.title ?:"Título não disponível"



                findViewById<TextView>(R.id.txt_description).text = article.description ?: article.content



            setOnClickListener {
                onItemClickListener?.let {click->
                    click(article)
                }
            }
        }
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnClickListener(listener: (Article)->Unit ){
        onItemClickListener = listener

    }

    fun removeItem(position: Int) {
        val currentList = differ.currentList.toMutableList()

        if (position >= 0 && position < currentList.size) {
            currentList.removeAt(position)
            differ.submitList(currentList)
        }

    }

     var originalList: List<Article>? = null
    fun setOriginalListInternal(list: List<Article>) {
        originalList = list
        differ.submitList(list)
    }

    fun search(query: String) {
        val filteredList = originalList?.filter { article ->
            article.title?.contains(query, ignoreCase = true) == true
        }

        differ.submitList(filteredList)
    }





}