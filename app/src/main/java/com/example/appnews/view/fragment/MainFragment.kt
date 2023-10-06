package com.example.appnews.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appnews.R
import com.example.appnews.databinding.FragmentMainBinding
import com.example.appnews.model.Article
import com.example.appnews.model.data.NewsDataSource
import com.example.appnews.presenter.ViewHome
import com.example.appnews.presenter.news.NewsPresenter
import com.example.appnews.util.btnBackSearch
import com.example.appnews.util.btnBackTextSubmitList
import com.example.appnews.util.buttonCloseSearch
import com.example.appnews.util.onBackPressed
import com.example.appnews.view.adapter.MainAdapter


class MainFragment : Fragment(), ViewHome.View {

    private val mainAdapter by lazy {
        MainAdapter()
    }


    private lateinit var mainPresenter: NewsPresenter

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var textNotFound: TextView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataSource = NewsDataSource(requireContext())
        mainPresenter = NewsPresenter(this, dataSource)
        mainPresenter.requestAll()


        configRecyclerView()
        searchNews()
        onBackPressed(this@MainFragment) { configBackPressed() }


        val title = binding.txtTitle.text.toString()
        com.example.appnews.util.articleCLick(mainAdapter, requireContext(),this, R.id.action_home_to_articleFragment, title)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun showProgressBar() {
        binding.progressMain.isVisible = true
    }

    override fun showFailure(message: String) {
        val bundle = Bundle().apply {
            putString("error", message)
        }
        findNavController().navigate(R.id.action_home_to_errorFragment, bundle)
    }



    override fun hideProgressBar() {
        binding.progressMain.isVisible = false
    }

    override fun showArticle(articles: List<Article>) {
        mainAdapter.differ.submitList(articles.toList())
    }



    private fun configRecyclerView(){
        with(binding.rvMain){
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL))
        }

    }


    private fun searchNews() {
        textNotFound = binding.txtNotFound

        val searchView = binding.searchNews
        val btnBack = binding.imgBtnBack


        searchView.setOnSearchClickListener {
            binding.imgBtnBack.isVisible = true
        }

        btnBackSearch(btnBack, searchView)

        val buttonClose: View?  = searchView?.findViewById(androidx.appcompat.R.id.search_close_btn)

        buttonCloseSearch(buttonClose, searchView, binding.imgBtnBack, false)




        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }


            override fun onQueryTextSubmit(query: String): Boolean {
                if (!query.isNullOrEmpty()) {
                    mainPresenter.search(query!!, textNotFound)
                    configRecyclerView()
                    binding.titleListNews.text = getString(R.string.result_search, query)
                    showProgressBar()


                    onBackPressed(this@MainFragment) { configBackPressed() }

                    buttonCloseSearch(buttonClose, searchView, binding.imgBtnBack, true)


                    btnBackTextSubmitList(btnBack, searchView, binding.txtNotFound)
                    { presenterRequestAll() }



                } ; return true
            }


        })

    }

    fun presenterRequestAll(){
        mainPresenter.requestAll()
        binding.titleListNews.text = getString(R.string.breaking_news)
    }

    fun configBackPressed(){
        mainPresenter.requestAll()
        binding.titleListNews.text = getString(R.string.breaking_news)
        textNotFound.isVisible= false
    }

}


