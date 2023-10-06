package com.example.appnews.model

import androidx.fragment.app.Fragment
import com.example.appnews.R

class CategoryList(val view: Fragment) {
    val list = listOf(
        Category(view.getString(R.string.world), "World", R.drawable.world ),
        Category(view.getString(R.string.sports), "Sports", R.drawable.sports),
        Category(view.getString(R.string.environment), "Environment", R.drawable.environment),
        Category(view.getString(R.string.technology), "Technology", R.drawable.technology),
        Category(view.getString(R.string.business), "Business", R.drawable.business),
        Category(view.getString(R.string.entertaiment), "Entertainment", R.drawable.entertaiment),
        Category(view.getString(R.string.politcs), "Politics", R.drawable.politics),
        Category(view.getString(R.string.science), "Science", R.drawable.science),
        Category(view.getString(R.string.health), "Health", R.drawable.health),
        Category(view.getString(R.string.food), "Food", R.drawable.food),
    )
}