package com.androiddevs.mvvmnewsapp.ui.fragment

import androidx.fragment.app.Fragment
import com.androiddevs.mvvmnewsapp.R

class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {

    lateinit var viewModel: NewsModel
    lateinit var newsAdapter: NewsAdapter
    val TAG = "SearchNewsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActiviity).viewModel
        setupRecycleView()

        var job: Job? = null
        etSearch.addTextChangedListener { editable ->
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

        viewModel.searchNews.observer(viewLifecycleOwner, observer { response ->
            when (response) {
                is Resource.Succes -> {
                    hideProgressBar()
                    hideProgressBar()
                    response.data?.let {
                        newsAdapter.differ.submitList(newsResponse.article)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An error accured: $message")

                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }

            }

            private fun hideProgressBar() {
                paginationProgressBar.visibility = INVISIBLE
            }

            private fun showProgressBar() {
                paginationProgressBar.visibility = VISIBLE
            }

            private fun setupRecycleView() {
                newsAdapter = NewsAdapter()
                rvSearchNews.apply {
                    adapter = newsAdapter
                    layoutManager = LinearLayoutManager(activity)
                }
            }
        })
    }
}