package com.example.appnews.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.appnews.MainActivity
import com.example.appnews.R
import com.example.appnews.databinding.FragmentArticleFavoriteBinding
import com.example.appnews.model.Article
import com.example.appnews.presenter.favorite.FavoritePresenter
import com.example.appnews.util.Constants
import com.example.appnews.util.initToolbar

class ArticleFavoriteFragment : Fragment() {
    private var _binding : FragmentArticleFavoriteBinding? = null
    private val binding get() = _binding!!


    private lateinit var article: Article


    private val args: ArticleFavoriteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentArticleFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar(binding.toolbar, "")

        onBackPressed()


        article = args.favoriteArticle!!
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

        shareLinkUrl()


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


    private fun shareLinkUrl() {
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

    private fun onBackPressed() {

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_articleFavoriteFragment_to_favorite)


            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }


}