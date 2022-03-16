package com.androiddevs.mvvmnewsapp.ui.fragment

import androidx.fragment.app.Fragment
import com.androiddevs.mvvmnewsapp.R

class ArticleFragment : Fragment(R.layout.fragment_article) {

    lateinit var viewModel: NewsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActiviity).viewModel
    }
}