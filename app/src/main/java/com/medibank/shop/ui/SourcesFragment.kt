package com.medibank.shop.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.medibank.shop.adapter.SourceAdapter
import com.medibank.shop.data.NewsDatabase
import com.medibank.shop.data.SourceRepository
import com.medibank.shop.databinding.FragmentSourcesBinding
import com.medibank.shop.viewmodel.SourcesViewModel
import com.medibank.shop.viewmodel.SourcesViewModelFactory

class SourcesFragment : Fragment() {

    private var _binding: FragmentSourcesBinding? = null
    private val binding: FragmentSourcesBinding
        get() = _binding!!

    private val sourceRepository: SourceRepository by lazy {
        SourceRepository(NewsDatabase.getInstance(requireContext()).sourceDao)
    }

    private val sourcesViewModel: SourcesViewModel by viewModels {
        SourcesViewModelFactory(sourceRepository)
    }

    private val adapter = SourceAdapter().apply {
        setHasStableIds(true)
        // Initialize the adapter to listen to selected changed events and update the selection
        // state
        onSourceSelectedChangedListener = { source, isSelected ->
            with(sourcesViewModel) { if (isSelected) select(source) else deselect(source) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSourcesBinding.inflate(inflater, container, false).apply {
            // Initialize the RecyclerView with the adapter
            recyclerView.adapter = adapter
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Submit the sources list to the adapter when a change is observed
        sourcesViewModel.sources.observe(viewLifecycleOwner) { sources ->
            adapter.submitList(sources)
        }
    }

    override fun onStart() {
        super.onStart()
        // Fetch the sources to ensure they are up to date
        sourcesViewModel.fetch()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}