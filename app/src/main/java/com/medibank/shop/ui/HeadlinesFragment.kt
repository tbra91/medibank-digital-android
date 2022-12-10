package com.medibank.shop.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.medibank.shop.viewmodel.HeadlinesViewModel

class HeadlinesFragment : ArticlesFragment() {

    private val headlinesViewModel: HeadlinesViewModel by viewModels()

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
