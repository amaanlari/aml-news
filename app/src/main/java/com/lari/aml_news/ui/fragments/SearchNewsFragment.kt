package com.lari.aml_news.ui.fragments

 import android.content.Context
 import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
 import android.view.inputmethod.InputMethodManager
 import android.widget.AbsListView
 import androidx.core.widget.addTextChangedListener
 import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
 import androidx.navigation.fragment.findNavController
 import androidx.recyclerview.widget.LinearLayoutManager
 import androidx.recyclerview.widget.RecyclerView
 import com.lari.aml_news.R
 import com.lari.aml_news.adapters.NewsAdapter
import com.lari.aml_news.databinding.FragmentSearchNewsBinding
import com.lari.aml_news.ui.NewsActivity
import com.lari.aml_news.ui.NewsViewModel
 import com.lari.aml_news.util.Constants
 import com.lari.aml_news.util.Constants.Companion.SEARCH_NEWS_TIME_DELAY
import com.lari.aml_news.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment() {

    val TAG: String = "SearchNewsFragment"
    lateinit var newsAdapter: NewsAdapter
    private var _binding : FragmentSearchNewsBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchNewsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Instantiate the view model using NewsActivity's view model.
        viewModel = (activity as NewsActivity).viewModel
        setupRecyclerView()

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_searchNewsFragment2_to_articleFragment,
                bundle
            )
        }

        binding.tilSearch.setEndIconOnClickListener {
            binding.tietSearch.clearFocus()
            val inputMethodManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(binding.root.windowToken, 0)
        }


        var job: Job? = null
        binding.tietSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.searchNews(editable.toString())
                    }
                }
            }
        }

        viewModel.searchNews.observe(viewLifecycleOwner, Observer {response ->
            when(response) {
                is Resource.Success -> {
                    hideProgessBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                        val totalPages = newsResponse.totalResults / Constants.QUERY_PAGE_SIZE + 2
                        isLastPage = viewModel.searchNewsPage == totalPages
                        if (isLastPage) {
                            binding.rvSearchNews.setPadding(0, 0, 0, 0)
                        }
                    }
                }
                is Resource.Error -> {
                    hideProgessBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An error occured : $message")
                    }
                }
                is Resource.Loading -> {
                    showProgessBar()
                }
            }
        })
    }

    private fun hideProgessBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgessBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if(shouldPaginate) {
                viewModel.searchNews(binding.tietSearch.text.toString())
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.rvSearchNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@SearchNewsFragment.scrollListener)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}