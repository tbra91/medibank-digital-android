package com.medibank.shop.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.medibank.shop.R
import com.medibank.shop.viewmodel.ArticleViewModel
import com.medibank.shop.viewmodel.SavesViewModel

class SavesFragment : ArticlesFragment() {

    private val savesViewModel: SavesViewModel by viewModels()

    private val articleViewModel: ArticleViewModel by navGraphViewModels(R.id.nav_graph)

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
