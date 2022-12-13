package com.medibank.shop.viewmodel

import androidx.lifecycle.*
import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.models.request.SourcesRequest
import com.kwabenaberko.newsapilib.models.response.SourcesResponse
import com.medibank.shop.api.NEWS_API_KEY
import com.medibank.shop.data.SourceEntity
import com.medibank.shop.data.SourceRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SourcesViewModel(private val sourceRepository: SourceRepository) : ViewModel() {

    private val _sources = MutableLiveData<List<SourceEntity>>()
    val sources: LiveData<List<SourceEntity>>
        get() = _sources

    /** Retrieves the [SourceEntity] list from News API and posts the results to [sources]. */
    fun fetch() {
        val newsApiClient = NewsApiClient(NEWS_API_KEY)
        val sourcesRequest = SourcesRequest.Builder().language("en").build()
        newsApiClient.getSources(sourcesRequest, object : NewsApiClient.SourcesCallback {
            override fun onSuccess(response: SourcesResponse?) {
                viewModelScope.launch {
                    // Transform each Source in the response to a SourceEntity and post the result
                    // to the sources LiveData
                    val sources = response?.sources?.map { source ->
                        SourceEntity(
                            source.id,
                            source.name,
                            source.description,
                            source.url,
                            source.category,
                            source.language,
                            source.country
                        ).apply {
                            // Set the isSelected property if the source exists in the repository
                            isSelected = sourceRepository.exists(this).first()
                        }
                    } ?: emptyList()
                    _sources.postValue(sources)
                }
            }

            override fun onFailure(throwable: Throwable?) {
                // TODO implement failure handling
            }
        })
    }

    /**
     * Sets the selected state of the [source] to true and inserts it into the [sourceRepository].
     *
     * @param source the [SourceEntity] to be selected
     */
    fun select(source: SourceEntity) {
        source.isSelected = true
        viewModelScope.launch { sourceRepository.insert(source) }
    }

    /**
     * Sets the selected state of the [source] to false and removes it from the
     * [sourceRepository].
     *
     * @param source the [SourceEntity] to be deselected
     */
    fun deselect(source: SourceEntity) {
        source.isSelected = false
        viewModelScope.launch { sourceRepository.delete(source) }
    }
}

class SourcesViewModelFactory(private val sourceRepository: SourceRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SourcesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return SourcesViewModel(sourceRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
