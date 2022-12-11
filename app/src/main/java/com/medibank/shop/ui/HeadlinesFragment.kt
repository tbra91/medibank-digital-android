package com.medibank.shop.ui

import android.os.Bundle
import android.view.View
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
            // Set the article on the ArticleViewModel and navigate to the ArticleFragment to
            // load the article
            articleViewModel.article = article
            findNavController().navigate(R.id.action_nav_headlines_fragment_to_nav_article_fragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with (headlinesViewModel) {
            // Submit the articles list to the adapter when a change is observed
            articles.observe(viewLifecycleOwner) { articles ->
                adapter.submitList(articles)
            }
            // Fetch articles with the new sources list when a change is observed
            sources.observe(viewLifecycleOwner) { sources ->
                fetch(sources)
            }
        }
    }
}
