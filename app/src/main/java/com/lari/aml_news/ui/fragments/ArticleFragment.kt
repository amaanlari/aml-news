 package com.lari.aml_news.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lari.aml_news.R
import com.lari.aml_news.databinding.FragmentArticleBinding


 class ArticleFragment : Fragment() {

     private var _binding : FragmentArticleBinding? = null
     private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

     override fun onDestroyView() {
         super.onDestroyView()
         _binding = null
     }
}