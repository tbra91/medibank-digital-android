package com.medibank.shop.viewmodel

import androidx.lifecycle.*
import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest
import com.kwabenaberko.newsapilib.models.response.ArticleResponse
import com.medibank.shop.api.NEWS_API_KEY
import com.medibank.shop.data.ArticleEntity
import com.medibank.shop.data.SourceEntity
import com.medibank.shop.data.SourceRepository

class HeadlinesViewModel(sourceRepository: SourceRepository) : ViewModel() {

    private val _articles = MutableLiveData<List<ArticleEntity>>().apply { value = emptyList() }
    val articles: LiveData<List<ArticleEntity>>
        get() = _articles

    val sources = sourceRepository.getAll().asLiveData()

    /**
     * Retrieves the headline [ArticleEntity] list from News API and posts the results to
     * [articles].
     *
     * @param sources the [SourceEntity] list to retrieve from
     */
    fun fetch(sources: List<SourceEntity>) {
        if (sources.isEmpty()) {
            // Just post an empty list to the articles LiveData if the sources list is empty
            _articles.postValue(emptyList())
        } else {
            // Build the request using the specified sources and fetch the top headlines from the
            // API
            val newsApiClient = NewsApiClient(NEWS_API_KEY)
            val sourceIds = sources.joinToString(",") { it.id }
            val topHeadlinesRequest = TopHeadlinesRequest.Builder().sources(sourceIds).build()
            newsApiClient.getTopHeadlines(topHeadlinesRequest,
                object : NewsApiClient.ArticlesResponseCallback {
                    override fun onSuccess(response: ArticleResponse?) {
                        // Transform each Article in the response to an ArticleEntity and post the
                        // result to the articles LiveData
                        val articles = response?.articles?.map { article ->
                            ArticleEntity(
                                article.source.id,
                                article.author,
                                article.title,
                                article.description,
                                article.url,
                                article.urlToImage,
                                article.publishedAt,
                                article.content
                            )
                        }
                        _articles.postValue(articles)
                    }

                    override fun onFailure(throwable: Throwable?) {
                        // TODO implement failure handling
                    }
                })
        }
    }
}

class HeadlinesViewModelFactory(private val sourceRepository: SourceRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HeadlinesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return HeadlinesViewModel(sourceRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
