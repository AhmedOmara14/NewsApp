package com.omaradev.newsapp.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omaradev.newsapp.data.repository.RemoteRequestStatus
import com.omaradev.newsapp.domain.model.AppApiResponse
import com.omaradev.newsapp.domain.model.news.Article
import com.omaradev.newsapp.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    var currentDate: String? = null

    var newsItemsList: ArrayList<Article> = ArrayList()
    val page = mutableStateOf(1)

    val pagesize: Int = 10
    private val apiKey: String = "885320cba4be43f785bd5f8c0c4c7217"
    private val language = Locale.getDefault().language

    var listScrollPosition = 0

    private var _articlesItems =
        MutableStateFlow<RemoteRequestStatus<AppApiResponse<List<Article>>>>(
            RemoteRequestStatus.ToggleLoading(false)
        )
    val articlesItems: MutableStateFlow<RemoteRequestStatus<AppApiResponse<List<Article>>>> get() = _articlesItems

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

}
