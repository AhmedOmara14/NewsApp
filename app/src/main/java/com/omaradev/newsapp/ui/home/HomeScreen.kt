package com.omaradev.newsapp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.omaradev.newsapp.R
import com.omaradev.newsapp.ui.home.component.ArticleItem
import com.omaradev.newsapp.ui.home.component.LabelText
import com.omaradev.newsapp.ui.home.component.SearchBar

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(), navController: NavController
) {
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

        }

        Box(modifier = Modifier.fillMaxSize()) {
            if (viewModel.newsItemsList.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
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
            } else {
                LazyColumn(modifier = Modifier.padding(top = 8.dp)) {
                    itemsIndexed(viewModel.newsItemsList) { index, t ->
                        ArticleItem(viewModel.newsItemsList[index]) {}
                    }
                }
            }
        }
    }
}