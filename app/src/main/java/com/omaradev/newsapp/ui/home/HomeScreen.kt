package com.omaradev.newsapp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.omaradev.newsapp.R
import com.omaradev.newsapp.data.repository.RemoteRequestStatus
import com.omaradev.newsapp.domain.model.news.Article
import com.omaradev.newsapp.navigation.HomeNavigation
import com.omaradev.newsapp.ui.home.component.ArticleItem
import com.omaradev.newsapp.ui.home.component.ArticleShimmer
import com.omaradev.newsapp.ui.home.component.LabelText
import com.omaradev.newsapp.ui.home.component.SearchBar
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(), navController: NavController
) {

    viewModel.currentDate = getFormattedDate()

    var isLoadingNewsItems by remember { mutableStateOf(false) }

    var searchValue by remember { mutableStateOf("") }

    val newsItemsState = viewModel.articlesItems.collectAsState()

    when (val newsItems = newsItemsState.value) {
        is RemoteRequestStatus.ToggleLoading -> isLoadingNewsItems = newsItems.showLoading

        is RemoteRequestStatus.OnSuccessRequest -> {
            isLoadingNewsItems = false
            viewModel.addNewArticles(newsItems.responseBody.articles as List<Article>)
        }

        is RemoteRequestStatus.OnFailedRequest -> {
            val exception = (newsItems as RemoteRequestStatus.OnFailedRequest<*>).errorMessage
        }

        else -> {}
    }

    Column(
        modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        LabelText(
            label = stringResource(id = R.string.main_label),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        )

        SearchBar {
            if (it.isNotEmpty()) {
                searchValue = it
                viewModel.apply {
                    getAllArticlesByTitle(searchValue)
                }
            }
        }
        HandleStateOfShowingData(isLoadingNewsItems, viewModel, searchValue, navController)
    }
}

fun getFormattedDate(): String {
    val calendar: Calendar = Calendar.getInstance()
    val currentDate: Date = calendar.time
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return dateFormat.format(currentDate)
}

@Composable
fun HandleStateOfShowingData(
    isLoadingNewsItems: Boolean,
    viewModel: HomeViewModel,
    searchValue: String,
    navController: NavController
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoadingNewsItems && viewModel.page.value == 1 -> ArticleShimmer()
            else -> {
                if (viewModel.newsItemsList.isEmpty()) NoSearchResults()
                else BindNewsData(viewModel, isLoadingNewsItems, searchValue, navController)
            }
        }
    }
}

@Composable
private fun NoSearchResults() {
    Column(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.ic_no_search_result),
            contentDescription = "",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .size(250.dp, 250.dp)
                .padding(top = 10.dp),
        )

        LabelText(
            label = stringResource(id = R.string.no_search_result),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        )
    }
}
@Composable
private fun BindNewsData(
    viewModel: HomeViewModel,
    isLoadingNewsItems: Boolean,
    searchValue: String,
    navController: NavController
) {
    LazyColumn(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxSize()
    ) {
        List(viewModel.newsItemsList.size) { index ->
            viewModel.onChangeArticlesListScrollPosition(index)
            // Check if you are near the end of the list and not loading more items
            if ((index + 1) >= (viewModel.page.value * viewModel.pagesize) && isLoadingNewsItems.not()) {
                viewModel.nextPage(searchValue)
            }
        }

        items(viewModel.newsItemsList.size) {
            ArticleItem(viewModel.newsItemsList[it]) { article ->
                navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
                navController.navigate(HomeNavigation.Details.screen)
            }
        }
    }
}
