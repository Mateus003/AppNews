package com.example.appnews.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.appnews.MainActivity
import com.example.appnews.R
import com.example.appnews.databinding.FragmentArticleBinding
import com.example.appnews.model.Article
import com.example.appnews.model.data.NewsDataSource
import com.example.appnews.presenter.ViewHome
import com.example.appnews.presenter.favorite.FavoritePresenter
import com.example.appnews.util.Constants
import com.example.appnews.util.initToolbar
import com.example.appnews.util.onBackPressed
import com.google.android.material.snackbar.Snackbar

class ArticleFragment : Fragment(), ViewHome.Favorite {
    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    private lateinit var article: Article

    private lateinit var presenter: FavoritePresenter

    private val args: ArticleFragmentArgs by navArgs()


    private lateinit var dataSource: NewsDataSource

    private var click: Boolean = false



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataSource = NewsDataSource(requireContext())

        showWebView()


        val getStringTitle = arguments?.getString(Constants.TITLE)

        onBackPressed(this){
          if (getStringTitle == "Nóticias"){
              findNavController().navigate(R.id.action_articleFragment_to_home)
          }else{
              findNavController().navigate(R.id.action_articleFragment_to_explore)

          }
      }


        initToolbar(binding.toolbar,getStringTitle!!)

        presenter = FavoritePresenter(this,dataSource)

        sharedLinkUrl()

    }

    override fun onResume() {
        super.onResume()
        val activity = requireActivity() as MainActivity
        activity.bottomNavigationView.visibility = View.GONE
    }

    override fun onPause() {
        super.onPause()
        val activity = requireActivity() as MainActivity
        activity.bottomNavigationView.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null

    }

    private fun showWebView() {

        article = args.article!!
        binding.articleWebView.apply {
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    binding.pgBar.isVisible = false

                }
            }
            loadUrl(article.link!!)
            this.settings.javaScriptEnabled = true
        }

            binding.imgButtonFavorite.setOnClickListener {
                if (!click) {
                    colorFavorite()
                    click = true
                    presenter.saveArticle(article)
                    Snackbar.make(it, "Nóticia salva", Snackbar.LENGTH_LONG).show()

                } else {
                    binding.imgButtonFavorite.setColorFilter(ContextCompat.getColor(requireContext(), R.color.white))
                    click = false
                    Snackbar.make(it, "Nóticia removida", Snackbar.LENGTH_LONG).show()
                    presenter.deleteArticle(article)

                }
        }
    }


    private fun colorFavorite(){
        binding.imgButtonFavorite.setColorFilter(ContextCompat.getColor(requireContext(), R.color.red))
    }

    override fun showArticle(article: List<Article>) {}

    private fun sharedLinkUrl() {
        binding.icShare.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, article.link)

            val chooser = Intent.createChooser(shareIntent, getString(R.string.share_link))
            if (shareIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(chooser)
            }
        }
    }
}