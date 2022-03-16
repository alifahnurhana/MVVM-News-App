package com.androiddevs.mvvmnewsapp.ui

import androidx.lifecycle.ViewModel
import com.androiddevs.mvvmnewsapp.model.NewsResponse
import com.androiddevs.mvvmnewsapp.repository.NewsRepository

class NewsViewModel(
    val newsResponse: NewsRepository
) : ViewModel() {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = mutableLiveData()
    var breakingNewsPage = 1

    val searchNews: MutableLiveData<Resource<NewsResponse>> = mutableLiveData()
    var searchNewsPage = 1

    init {
        getBreakingNews("us")
    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading)
        val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response = NewsRepository.searchNews(searchQuery, searchNewsPage)
        searchNews.postValue(handleSearchNewResponse(response))
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Succes(resultResponse)

            }
        }
        return Resource.Error(response.messege())
    }

    private fun handleSearchNewResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Succes(resultResponse)

            }
        }
        return Resource.Error(response.messege())
    }

}