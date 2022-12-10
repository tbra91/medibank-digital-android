package com.medibank.shop.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest
import com.kwabenaberko.newsapilib.models.response.ArticleResponse
import com.medibank.shop.api.NEWS_API_KEY
import com.medibank.shop.data.*
import kotlinx.coroutines.launch

class HeadlinesViewModel(application: Application) : AndroidViewModel(application) {

    private val _articles = MutableLiveData<List<ArticleEntity>>().apply { value = emptyList() }
    val articles: LiveData<List<ArticleEntity>>
        get() = _articles

    val sources =
        SourceRepository(NewsDatabase.getInstance(application).sourceDao).getAll().asLiveData()

    fun fetch(sources: List<SourceEntity>) {
        val newsApiClient = NewsApiClient(NEWS_API_KEY)
        val topHeadlinesRequest = TopHeadlinesRequest.Builder().sources(sources.joinToString(", ")).build()
        newsApiClient.getTopHeadlines(topHeadlinesRequest, object : NewsApiClient.ArticlesResponseCallback {
            override fun onSuccess(response: ArticleResponse?) {
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
            }
        })
    }
}