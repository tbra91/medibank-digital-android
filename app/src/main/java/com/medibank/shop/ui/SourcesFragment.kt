package com.medibank.shop.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.kwabenaberko.newsapilib.models.Source
import com.medibank.shop.adapter.SourceAdapter
import com.medibank.shop.databinding.FragmentSourcesBinding
import com.medibank.shop.viewmodel.SourcesViewModel

class SourcesFragment : Fragment() {

    private var _binding: FragmentSourcesBinding? = null
    private val binding: FragmentSourcesBinding
        get() = _binding!!

    private val sourcesViewModel: SourcesViewModel by viewModels()

    private val adapter: SourceAdapter by lazy {
        SourceAdapter().apply { submitList(sourcesViewModel.sources.value ?: emptyList()) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSourcesBinding.inflate(inflater, container, false).apply {
            // Initialize the RecyclerView
            recyclerView.adapter = adapter
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sourcesViewModel.sources.observe(viewLifecycleOwner) { sources: List<Source>? ->
            adapter.submitList(sources)
        }
    }

    override fun onStart() {
        super.onStart()
        sourcesViewModel.fetch()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}