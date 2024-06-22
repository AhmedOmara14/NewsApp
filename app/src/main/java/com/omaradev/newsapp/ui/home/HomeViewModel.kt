package com.omaradev.newsapp.ui.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omaradev.domain.model.AppApiResponse
import com.omaradev.domain.model.news.Article
import com.omaradev.domain.repository.RemoteRequestStatus
import com.omaradev.domain.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.dsl.module
import java.util.Locale

val ViewModelModule = module {
    single<HomeViewModel> { HomeViewModel(get()) }
}

class HomeViewModel(
    private val repository: Repository
) : ViewModel() {

    var currentDate: String? = null

    var newsItemsList: ArrayList<Article> = ArrayList()

    var savedNewsItemsList = mutableStateOf<List<Article>>(emptyList())

    val page = mutableStateOf(1)

    val pagesize: Int = 10
    private val apiKey: String = "a726297be93d42ffbd7cbdb254b39f56"
    private val language = Locale.getDefault().language

    var listScrollPosition = 0

    private var _articlesItems =
        MutableStateFlow<RemoteRequestStatus<AppApiResponse<List<Article>>>>(
            RemoteRequestStatus.ToggleLoading(false)
        )
    val articlesItems: StateFlow<RemoteRequestStatus<AppApiResponse<List<Article>>>> get() = _articlesItems

    fun getAllArticlesByTitle(
        searchValue: String
    ) {
        resetList()
        loadAllArticles(searchValue)
    }

    private fun loadAllArticles(searchValue: String) = viewModelScope.launch {
        currentDate?.let {
            repository.getAllArticlesByTitle(
                page = page.value,
                pageSize = pagesize,
                language = language,
                searchValue = searchValue,
                apiKey = apiKey
            ).collectLatest {
                _articlesItems.value = it
            }
        }
    }

    private fun resetList() {
        newsItemsList.clear()
        page.value = 1
        listScrollPosition = 0
    }

    fun nextPage(searchValue: String) {
        viewModelScope.launch {
            if ((listScrollPosition + 1) >= (page.value * pagesize)) {
                incrementPage()
                if (page.value > 1) {
                    loadAllArticles(searchValue)
                }
            }
        }
    }

    private fun incrementPage() {
        page.value += 1
    }

    fun onChangeArticlesListScrollPosition(position: Int) {
        listScrollPosition = position
    }

    fun addNewArticles(articles: List<Article>) {
        val uniqueNewArticles = articles.filter { newArticle ->
            newsItemsList.none { existingArticle ->
                existingArticle == newArticle
            }
        }
        newsItemsList.addAll(uniqueNewArticles)
    }

    fun insertArticle(article: Article) {
        viewModelScope.launch {
            repository.insertArticle(article)
        }
    }

    fun removeAllArticles() {
        viewModelScope.launch {
            repository.deleteAllArticles().onEach {}.launchIn(viewModelScope)
        }
    }

    fun getSavedArticles(): MutableState<List<Article>> {
        viewModelScope.launch {
            val uniqueNewArticles = repository.getAllArticles().filter { newArticle ->
                newsItemsList.none { existingArticle ->
                    existingArticle.id == newArticle.id
                }
            }
            savedNewsItemsList.value = uniqueNewArticles as ArrayList<Article>
        }
        return savedNewsItemsList
    }
}
