package com.medibank.shop.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.medibank.shop.R
import com.medibank.shop.data.ArticleRepository
import com.medibank.shop.data.NewsDatabase
import com.medibank.shop.data.SourceRepository
import com.medibank.shop.viewmodel.*

class HeadlinesFragment : ArticlesFragment() {

    private val articleRepository: ArticleRepository by lazy {
        ArticleRepository(NewsDatabase.getInstance(requireContext()).articleDao)
    }

    private val sourceRepository: SourceRepository by lazy {
        SourceRepository(NewsDatabase.getInstance(requireContext()).sourceDao)
    }

    private val headlinesViewModel: HeadlinesViewModel by viewModels {
        HeadlinesViewModelFactory(sourceRepository)
    }

    private val articleViewModel: ArticleViewModel by navGraphViewModels(R.id.nav_graph) {
        ArticleViewModelFactory(articleRepository)
    }

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
