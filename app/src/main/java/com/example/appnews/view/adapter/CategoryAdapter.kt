package com.example.appnews.view.adapter

import android.content.ClipData
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.toColorInt
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory

import com.example.appnews.R
import com.example.appnews.model.Article
import com.example.appnews.model.Category
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

class CategoryAdapter(val category: Category): Item<CategoryAdapter.CategoryViewHolder>(){
    inner class CategoryViewHolder(view: View): GroupieViewHolder(view)

    override fun createViewHolder(itemView: View): CategoryViewHolder {
        return CategoryViewHolder(itemView)
    }
    override fun bind(viewHolder: CategoryViewHolder, position: Int) {
        viewHolder.itemView.findViewById<TextView>(R.id.txt_category).text = category.textCategory

        val imageView = viewHolder.itemView.findViewById<ImageView>(R.id.imageCategory)
        Glide.with(viewHolder.itemView)
            .load(category.imageCategory)
            .transition(DrawableTransitionOptions.withCrossFade(DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()))
            .into(imageView)


    }

    override fun getLayout(): Int = R.layout.item_category






}