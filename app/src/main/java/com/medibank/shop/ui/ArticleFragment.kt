package com.medibank.shop.ui

import android.os.Bundle
import android.view.*
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import com.medibank.shop.R
import com.medibank.shop.data.ArticleRepository
import com.medibank.shop.data.NewsDatabase
import com.medibank.shop.viewmodel.ArticleViewModel
import com.medibank.shop.viewmodel.ArticleViewModelFactory

class ArticleFragment : Fragment() {

    private val articleRepository: ArticleRepository by lazy {
        ArticleRepository(NewsDatabase.getInstance(requireContext()).articleDao)
    }

    private val articleViewModel: ArticleViewModel by navGraphViewModels(R.id.nav_graph) {
        ArticleViewModelFactory(articleRepository)
    }

    private var isSaved = false
        set(value) {
            if (field != value) {
                field = value
                activity?.invalidateMenu()
            }
        }

    private val menuProvider = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            with(menuInflater) {
                if (isSaved) {
                    inflate(R.menu.article_delete_menu, menu)
                } else {
                    inflate(R.menu.article_save_menu, menu)
                }
            }
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            when (menuItem.itemId) {
                R.id.menu_save -> {
                    // Save the article and display a Toast to the user
                    articleViewModel.save()
                    Toast.makeText(requireContext(), R.string.article_saved, Toast.LENGTH_SHORT)
                        .show()
                    return true
                }
                R.id.menu_delete -> {
                    // Delete the article and display a Toast to the user
                    articleViewModel.delete()
                    Toast.makeText(requireContext(), R.string.article_deleted, Toast.LENGTH_SHORT)
                        .show()
                    return true
                }
            }

            return false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return WebView(requireContext()).apply {
            // Initialize the WebView
            webViewClient = WebViewClient()
            articleViewModel.article?.let { article -> loadUrl(article.url) }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Update the saved state when a change is observed
        articleViewModel.isSaved()?.observe(viewLifecycleOwner) { isSaved = it }
    }

    override fun onStart() {
        super.onStart()
        requireActivity().addMenuProvider(menuProvider, this)
    }

    override fun onStop() {
        super.onStop()
        requireActivity().removeMenuProvider(menuProvider)
    }
}
