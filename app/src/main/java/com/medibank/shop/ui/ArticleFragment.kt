package com.medibank.shop.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.medibank.shop.viewmodel.ArticleViewModel

class ArticleFragment : Fragment() {

    private val articleViewModel: ArticleViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return WebView(requireContext()).apply {
            webViewClient = WebViewClient()
            articleViewModel.article?.let { article -> loadUrl(article.url) }
        }
    }
}
