package com.medibank.shop

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import com.medibank.shop.data.ArticleEntity
import com.medibank.shop.data.ArticleRepository
import com.medibank.shop.data.NewsDatabase
import com.medibank.shop.viewmodel.SavesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.*

@ExperimentalCoroutinesApi
class SavesViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // TODO use mock repository
    private val articleRepository: ArticleRepository by lazy {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        ArticleRepository(NewsDatabase.getInstance(context).articleDao)
    }

    private lateinit var savesViewModel: SavesViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        savesViewModel = SavesViewModel(articleRepository)
    }

    @Test
    fun containsArticle() = runTest {
        // Initialize the article
        val article = ArticleEntity(
            null,
            null,
            null,
            null,
            "http://www.abc.net.au/news/2022-12-11/rock-chicks-plesiosaur-fossil-palaeontology-discovery-outback/101755418",
            null,
            null,
            null
        )

        // Observe the LiveData so it emits data
        val articles = savesViewModel.articles.apply {
            observeForever {}
        }

        launch {articleRepository.insert(article) }
        advanceUntilIdle()
        Assert.assertTrue(articles.value!!.contains(article))

        launch {articleRepository.delete(article) }
        advanceUntilIdle()
        Assert.assertFalse(articles.value!!.contains(article))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}