package com.lari.aml_news.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lari.aml_news.R
import com.lari.aml_news.databinding.FragmentSavedNewsBinding
import com.lari.aml_news.ui.NewsActivity
import com.lari.aml_news.ui.NewsViewModel

class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {
    private var _binding : FragmentSavedNewsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSavedNewsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Instantiate the view model using NewsActivity's view model.
        viewModel = (activity as NewsActivity).viewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}