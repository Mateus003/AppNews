package com.example.appnews.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appnews.R
import com.example.appnews.databinding.FragmentExploreBinding
import com.example.appnews.model.Article
import com.example.appnews.model.Category
import com.example.appnews.model.CategoryList
import com.example.appnews.model.data.ExploreDataSource
import com.example.appnews.presenter.ViewHome
import com.example.appnews.presenter.explore.ExplorePresenter
import com.example.appnews.util.articleCLick
import com.example.appnews.util.btnBackSearch
import com.example.appnews.util.btnBackTextSubmitList
import com.example.appnews.util.buttonCloseSearch
import com.example.appnews.util.onBackPressed
import com.example.appnews.view.adapter.CategoryAdapter
import com.example.appnews.view.adapter.MainAdapter
import com.xwray.groupie.GroupieAdapter


class ExploreFragment : Fragment(), ViewHome.View {

    private  var _binding : FragmentExploreBinding?=null
    private val binding get() = _binding!!

    private lateinit var presenter: ExplorePresenter

    private val adapter by lazy {
        GroupieAdapter()
    }

    private val mainAdapter by lazy {
        MainAdapter()
    }

    private lateinit var textCategory: TextView

    private lateinit var textNotFound: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataSource = ExploreDataSource()
        presenter = ExplorePresenter(dataSource, this)

        configRecyclerViewCategory()
        addCategoryRecyclerView()


        configRecyclerViewArticle()

        //A categoria mundo virá como padrão
        textCategory = binding.titleListNews
        presenter.category("world",textCategory,getString(R.string.world))


        searchNews()

        val titleExplore = binding.titleExplore.text.toString()
        articleCLick(mainAdapter, requireContext(), this, R.id.action_explore_to_articleFragment,titleExplore )

        categoryClick()

        showProgressBar()

    }

    private fun configRecyclerViewArticle(){
        with(binding.rvMain){
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(requireContext(),
                    DividerItemDecoration.VERTICAL)
            )
        }
    }


    private fun categoryClick(){
        adapter.setOnItemClickListener { item, view ->
            val categoryName = (item as CategoryAdapter).category.textCategoryEndpoint.lowercase()
            val textCategory = binding.titleListNews
            val categoryBr = item.category.textCategory
            showProgressBar()
            presenter.category(categoryName, textCategory, categoryBr)
        }
    }

    private fun configRecyclerViewCategory(){
        val recyclerView = binding.rvCategory
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter
    }

    private fun addCategoryRecyclerView() {
        val categoryList = CategoryList(this).list

        categoryList.forEach { category ->
            val categoryAdapter = CategoryAdapter(category)
            adapter.add(categoryAdapter)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun showProgressBar() {
        binding.progressMain.isVisible = true
    }

    override fun showFailure(message: String) {

    }

    override fun hideProgressBar() {
        binding.progressMain.isVisible = false
    }

    override fun showArticle(articles: List<Article>) {
        mainAdapter.differ.submitList(articles.toList())
        binding.txtNotFound.isVisible = articles.isEmpty()
    }


    private fun searchNews() {

        textNotFound = binding.txtNotFound
        val searchView = binding.searchNews
        val btnBack = binding.imgBtnBack

        searchView.setOnSearchClickListener {
            btnBack.isVisible = true
        }

       btnBackSearch(btnBack, searchView)

        val buttonClose: View?  = searchView?.findViewById(androidx.appcompat.R.id.search_close_btn)
        buttonCloseSearch(buttonClose, searchView, binding.imgBtnBack, false)

        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    presenter.search(query!!, textNotFound)
                    binding.rvCategory.isVisible = false
                    binding.titleListNews.text = getString(R.string.result_search, query)

                  onBackPressed(this@ExploreFragment){configBackPressed()}

                    buttonCloseSearch(buttonClose, searchView, binding.imgBtnBack, true)

                    btnBackTextSubmitList(btnBack, searchView, binding.txtNotFound)
                    { presenterCategoryWorld() }

                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    fun configBackPressed(){
        presenter.category("world", textCategory, getString(R.string.world))
        binding.rvCategory.isVisible = true
        textNotFound.isVisible= false
    }
    fun presenterCategoryWorld(){
        presenter.category("world", textCategory, getString(R.string.world))
        binding.rvCategory.isVisible = true
    }
}