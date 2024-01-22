 package com.lari.aml_news.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.lari.aml_news.R
import com.lari.aml_news.databinding.FragmentArticleBinding
import com.lari.aml_news.ui.NewsActivity
import com.lari.aml_news.ui.NewsViewModel


 class ArticleFragment : Fragment(R.layout.fragment_article) {

     private var _binding : FragmentArticleBinding? = null
     private val binding get() = _binding!!

     private lateinit var viewModel: NewsViewModel
     val args: ArticleFragmentArgs by navArgs()

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
         val article = args.article
         Log.e("ArticleFragment", "this is the ${article.title}")
         binding.webView.apply {
             webViewClient = WebViewClient()
             article.url?.let { loadUrl(it) }
             true.also { settings.javaScriptEnabled }
         }

         binding.fab.setOnClickListener {
             viewModel.saveArticle(article)
             Snackbar.make(view, "Article saved successfully", Snackbar.LENGTH_SHORT).show()
         }
     }

     override fun onDestroyView() {
         super.onDestroyView()
         _binding = null
     }
}