package com.medibank.shop.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import com.medibank.shop.R
import com.medibank.shop.adapter.ArticleAdapter
import com.medibank.shop.databinding.FragmentArticlesBinding
import com.medibank.shop.viewmodel.ArticleViewModel

abstract class ArticlesFragment : Fragment() {

    private var _binding: FragmentArticlesBinding? = null
    private val binding: FragmentArticlesBinding
        get() = _binding!!

    private val articleViewModel: ArticleViewModel by activityViewModels()

    protected val adapter = ArticleAdapter().apply {
        onArticleClickListener = { article ->
            articleViewModel.article = article
            parentFragmentManager.commit {
                setReorderingAllowed(true)
                setCustomAnimations(
                    R.anim.nav_default_enter_anim,
                    R.anim.nav_default_exit_anim,
                    R.anim.nav_default_pop_enter_anim,
                    R.anim.nav_default_pop_exit_anim
                )
                replace(R.id.nav_host_fragment, ArticleFragment::class.java, null)
                addToBackStack(null)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticlesBinding.inflate(inflater, container, false).apply {
            // Initialize the RecyclerView with the ArticleAdapter
            recyclerView.adapter = adapter
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
