package com.androiddevs.mvvmnewsapp.ui.fragment

import androidx.fragment.app.Fragment
import com.androiddevs.mvvmnewsapp.R

class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {

    lateinit var viewModel : NewsViewModel

    override fun onViewCreated(view: android.view.View, savedInstanceState: android.os.Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActiviity).viewModel
    }
}