 package com.lari.aml_news.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lari.aml_news.R
import com.lari.aml_news.databinding.FragmentArticleBinding
import com.lari.aml_news.ui.NewsActivity
import com.lari.aml_news.ui.NewsViewModel


 class ArticleFragment : Fragment(R.layout.fragment_article) {

     private var _binding : FragmentArticleBinding? = null
     private val binding get() = _binding!!

     private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
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