package com.medibank.shop.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.medibank.shop.R
import com.medibank.shop.viewmodel.ArticleViewModel
import com.medibank.shop.viewmodel.HeadlinesViewModel

class HeadlinesFragment : ArticlesFragment() {

    private val headlinesViewModel: HeadlinesViewModel by viewModels()

    private val articleViewModel: ArticleViewModel by navGraphViewModels(R.id.nav_graph)

    init {
        adapter.onArticleClickListener = { article ->
            articleViewModel.article = article
            findNavController().navigate(R.id.action_nav_headlines_to_nav_article)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with (headlinesViewModel) {
            articles.observe(viewLifecycleOwner) { articles ->
                adapter.submitList(articles)
            }
            sources.observe(viewLifecycleOwner) { sources ->
                fetch(sources)
            }
        }
    }
}
