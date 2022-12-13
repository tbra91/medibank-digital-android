package com.medibank.shop

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import com.medibank.shop.data.ArticleEntity
import com.medibank.shop.data.ArticleRepository
import com.medibank.shop.data.NewsDatabase
import com.medibank.shop.viewmodel.ArticleViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*

@ExperimentalCoroutinesApi
class ArticleViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val articleRepository: ArticleRepository by lazy {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        ArticleRepository(NewsDatabase.getInstance(context).articleDao)
    }

    private lateinit var articleViewModel: ArticleViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        articleViewModel = ArticleViewModel(articleRepository)
    }

    @Test
    fun saveAndDelete() = runTest {
        // Initialize the article on the articleViewModel
        articleViewModel.article = ArticleEntity(
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
        val isSaved = articleViewModel.isSaved()!!.apply {
            observeForever {}
        }

        // Test save
        articleViewModel.save()
        advanceUntilIdle()
        Assert.assertTrue(isSaved.value!!)

        // Test delete
        articleViewModel.delete()
        advanceUntilIdle()
        Assert.assertFalse(isSaved.value!!)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
