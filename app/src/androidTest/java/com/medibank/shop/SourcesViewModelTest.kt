package com.medibank.shop

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.platform.app.InstrumentationRegistry
import com.medibank.shop.data.NewsDatabase
import com.medibank.shop.data.SourceEntity
import com.medibank.shop.data.SourceRepository
import com.medibank.shop.viewmodel.SourcesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.*

@ExperimentalCoroutinesApi
class SourcesViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // TODO use mock repository
    private val sourceRepository: SourceRepository by lazy {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        SourceRepository(NewsDatabase.getInstance(context).sourceDao)
    }

    private lateinit var sourcesViewModel: SourcesViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        sourcesViewModel = SourcesViewModel(sourceRepository)
    }

    @Test
    fun selectAndDeselect() = runTest {
        // Initialize the source
        val source = SourceEntity(
            "abc-news-au",
            null,
            null,
            null,
            null,
            null,
            null,
        )

        // Test select
        sourcesViewModel.select(source)
        advanceUntilIdle()
        Assert.assertTrue(sourceRepository.exists(source).first())
        advanceUntilIdle()

        // Test deselect
        sourcesViewModel.deselect(source)
        advanceUntilIdle()
        Assert.assertFalse(sourceRepository.exists(source).first())
        advanceUntilIdle()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}