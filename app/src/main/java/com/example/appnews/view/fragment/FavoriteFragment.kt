package com.example.appnews.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appnews.R
import com.example.appnews.databinding.FragmentFavoriteBinding
import com.example.appnews.model.Article
import com.example.appnews.model.data.NewsDataSource
import com.example.appnews.model.data.NewsRepository
import com.example.appnews.model.local.dao.ArticleDAO
import com.example.appnews.presenter.ViewHome
import com.example.appnews.presenter.favorite.FavoritePresenter
import com.example.appnews.util.Constants.TITLE
import com.example.appnews.util.buttonCloseSearch
import com.example.appnews.view.adapter.MainAdapter
import com.google.android.material.snackbar.Snackbar


class FavoriteFragment : Fragment(), ViewHome.Favorite {

    private val mainAdapter by lazy {
        MainAdapter()
    }

    private var _binding:FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: FavoritePresenter

    private lateinit var newsDataSource: NewsDataSource


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsDataSource = NewsDataSource(requireContext())

        configRecyclerView()





        presenter = FavoritePresenter(this, newsDataSource)
        presenter.getAll()

        articleCLick()

        searchFavoriteNews()



        val itemTouchPerCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition
                val article = mainAdapter.differ.currentList[position]
                presenter.deleteArticle(article)

                mainAdapter.removeItem(position)

                Snackbar.make(viewHolder.itemView, R.string.article_delete_successful, Toast.LENGTH_SHORT).apply {
                    setAction(getString(R.string.undo)){
                        presenter.saveArticle(article)
                        mainAdapter.notifyDataSetChanged()
                        presenter.getAll()

                    }



                }.show()
            }



        }

        ItemTouchHelper(itemTouchPerCallback).apply {
            attachToRecyclerView(binding.rvFavorite)
        }

        presenter.getAll()


        Log.d("TamanhoAdapter", mainAdapter.originalList.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showArticle(article: List<Article>) {
        mainAdapter.setOriginalListInternal(article)

    }

    private fun configRecyclerView(){
        with(binding.rvFavorite){
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
            addItemDecoration(
                DividerItemDecoration(requireContext(),
                    androidx.recyclerview.widget.DividerItemDecoration.VERTICAL)
            )


        }

    }

    private fun articleCLick() {
        mainAdapter.setOnClickListener { article ->
            if (article.link != null) {
                val bundle = Bundle().apply {
                    putSerializable("favorite_article", article)
                }
                findNavController().navigate(R.id.action_favorite_to_articleFavoriteFragment, bundle)
            } else {
                Toast.makeText(requireContext(), "O link do artigo está indisponível", Toast.LENGTH_LONG).show()
            }
        }

    }

private fun searchFavoriteNews(){

    val searchView = (binding.searchNews)

    val btnBack = binding.imgBtnBack



    searchView.setOnSearchClickListener {
        binding.imgBtnBack.isVisible = true
    }

    btnBack.setOnClickListener {
        btnBack.isVisible = false
        searchView.onActionViewCollapsed()
        searchView.setQuery("", false)
        searchView.clearFocus()

    }

    val buttonClose: View?  = searchView?.findViewById(androidx.appcompat.R.id.search_close_btn)

    buttonCloseSearch(buttonClose, searchView, binding.imgBtnBack, false)

    searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
        override fun onQueryTextSubmit(query: String?): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            if (!newText.isNullOrEmpty()){

                mainAdapter.search(newText!!)

                buttonCloseSearch(buttonClose, searchView, binding.imgBtnBack, true)


                btnBack.setOnClickListener {
                    btnBack.isVisible = false

                    searchView.clearFocus()
                    searchView.setQuery("", false)

                    searchView.onActionViewCollapsed()
                    btnBack.isVisible = false
                    showArticle(mainAdapter.originalList!!)


                }

                btnBack.isVisible = true

            }

            return true
        }
    })
}


}