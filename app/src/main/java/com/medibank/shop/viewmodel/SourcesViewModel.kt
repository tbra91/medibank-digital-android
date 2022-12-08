package com.medibank.shop.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.models.request.SourcesRequest
import com.kwabenaberko.newsapilib.models.response.SourcesResponse
import com.medibank.shop.api.NEWS_API_KEY
import com.medibank.shop.data.NewsDatabase
import com.medibank.shop.data.SourceEntity
import com.medibank.shop.data.SourceRepository
import kotlinx.coroutines.launch

class SourcesViewModel(application: Application) : AndroidViewModel(application) {

    private val sourceRepository = SourceRepository(NewsDatabase.getInstance(application).sourceDao)

    private val _sources = MutableLiveData<List<SourceEntity>>()
    val sources: LiveData<List<SourceEntity>>
        get() = _sources

    fun fetch() {
        val newsApiClient = NewsApiClient(NEWS_API_KEY)
        val sourcesRequest = SourcesRequest.Builder().language("en").build()
        newsApiClient.getSources(sourcesRequest, object : NewsApiClient.SourcesCallback {
            override fun onSuccess(response: SourcesResponse?) {
                viewModelScope.launch {
                    val sources = response?.sources?.map { source ->
                        sourceRepository.get(source.id)?.apply {
                            name = source.name
                            description = source.description
                            url = source.url
                            category = source.category
                            language = source.language
                            country = source.country
                            isSelected = true
                        }?.also {
                            sourceRepository.update(it)
                        } ?: SourceEntity(
                            source.id,
                            source.name,
                            source.description,
                            source.url,
                            source.category,
                            source.language,
                            source.country
                        )
                    } ?: emptyList()
                    _sources.postValue(sources)
                }
            }

            override fun onFailure(throwable: Throwable?) {
            }
        })
    }

    fun select(source: SourceEntity) {
        viewModelScope.launch { sourceRepository.insert(source) }
    }

    fun deselect(source: SourceEntity) {
        viewModelScope.launch { sourceRepository.delete(source) }
    }
}
