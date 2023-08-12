package com.omaradev.newsapp.ui.home

import com.omaradev.domain.model.news.Article
import com.omaradev.domain.model.news.Source
import com.omaradev.newsapp.data.repository.FakeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(TestCoroutineDispatcher()) // Set up the main dispatcher
        viewModel = HomeViewModel(repository = FakeRepository())
    }

    @Test
    fun `test add new articles, return true`() {
        val existingArticle = Article(
            id = 1,
            author = "author_test_1",
            content = "content_test_1",
            description = "description_test_1",
            publishedAt = "2022-2-22",
            source = Source(name = "name_test_1"),
            title = "title_test_1",
            url = "url_test_1",
            urlToImage = "urlToImage_test_1",
        )
        val newArticle = Article(
            id = 2,
            author = "author_test_2",
            content = "content_test_2",
            description = "description_test_2",
            publishedAt = "2023-3-3",
            source = Source(name = "name_test_2"),
            title = "title_test_2",
            url = "url_test_2",
            urlToImage = "urlToImage_test_2",
        )

        viewModel.newsItemsList.add(existingArticle)

        viewModel.addNewArticles(listOf(newArticle))


        assert(viewModel.newsItemsList.contains(newArticle))
    }

    @Test
    fun `test insert article, return true`() = runBlocking {
        val article = Article(
            id = 1,
            author = "author_test_1",
            content = "content_test_1",
            description = "description_test_1",
            publishedAt = "2022-2-22",
            source = Source(name = "name_test_1"),
            title = "title_test_1",
            url = "url_test_1",
            urlToImage = "urlToImage_test_1",
        )
        viewModel.insertArticle(article)

        val result = viewModel.getSavedArticles().value

        assert(result.contains(article))
    }

    @Test
    fun `test remove all articles, return true`() {
        val article = Article(
            id = 1,
            author = "author_test_1",
            content = "content_test_1",
            description = "description_test_1",
            publishedAt = "2022-2-22",
            source = Source(name = "name_test_1"),
            title = "title_test_1",
            url = "url_test_1",
            urlToImage = "urlToImage_test_1",
        )
        viewModel.insertArticle(article)

        viewModel.removeAllArticles()

        assert(viewModel.getSavedArticles().value.contains(article).not())
    }

}
