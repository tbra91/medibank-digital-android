package com.medibank.shop.viewmodel

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.models.Source
import com.kwabenaberko.newsapilib.models.request.SourcesRequest
import com.kwabenaberko.newsapilib.models.response.SourcesResponse
import com.medibank.shop.api.NEWS_API_KEY

class SourcesViewModel : ViewModel() {
    private val _sources: MutableLiveData<List<Source>> by lazy {
        MutableLiveData<List<Source>>().apply { value = emptyList() }
    }
    val sources: LiveData<List<Source>>
        get() = _sources

    @MainThread
    fun fetch(category: String? = null, language: String? = "en", country: String? = null) {
        val newsApiClient = NewsApiClient(NEWS_API_KEY)
        val sourcesRequest =
            SourcesRequest.Builder().category(category).language(language).country(country).build()
        newsApiClient.getSources(sourcesRequest, object : NewsApiClient.SourcesCallback {
            override fun onSuccess(response: SourcesResponse?) {
                _sources.value = response?.sources ?: emptyList()
            }

            override fun onFailure(throwable: Throwable?) {
            }
        })
    }
}