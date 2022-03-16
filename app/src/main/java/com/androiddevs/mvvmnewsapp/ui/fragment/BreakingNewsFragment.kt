package com.androiddevs.mvvmnewsapp.ui.fragment

import androidx.fragment.app.Fragment
import com.androiddevs.mvvmnewsapp.R

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActiviity).viewModel
        setupRecycleView()

        viewModel.breakingNews.observe(viewLifecycleOwner, observer { response ->
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
        })
    }

    private fun hideProgressBar() {
        paginationProgressBar.visibility = INVISIBLE
    }

    private fun showProgressBar() {
        paginationProgressBar.visibility = VISIBLE
    }

    private fun setupRecycleView() {
        newsAdapter = NewsAdapter()
        rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }


}