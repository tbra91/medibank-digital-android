package com.medibank.shop.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.medibank.shop.R
import com.medibank.shop.data.ArticleRepository
import com.medibank.shop.data.NewsDatabase
import com.medibank.shop.viewmodel.ArticleViewModel
import com.medibank.shop.viewmodel.ArticleViewModelFactory
import com.medibank.shop.viewmodel.SavesViewModel
import com.medibank.shop.viewmodel.SavesViewModelFactory

class SavesFragment : ArticlesFragment() {

    private val articleRepository: ArticleRepository by lazy {
        ArticleRepository(NewsDatabase.getInstance(requireContext()).articleDao)
    }

    private val savesViewModel: SavesViewModel by viewModels {
        SavesViewModelFactory(articleRepository)
    }

    private val articleViewModel: ArticleViewModel by navGraphViewModels(R.id.nav_graph) {
        ArticleViewModelFactory(articleRepository)
    }

    init {
        // Initialize the adapter
        adapter.onArticleClickListener = { article ->
            articleViewModel.article = article
            findNavController().navigate(R.id.action_nav_saves_fragment_to_nav_article_fragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Submit the articles list to the adapter when a change is observed
        savesViewModel.articles.observe(viewLifecycleOwner) { articles ->
            adapter.submitList(articles)
        }
    }
}
