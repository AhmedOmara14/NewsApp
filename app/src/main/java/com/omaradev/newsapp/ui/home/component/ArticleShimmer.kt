package com.omaradev.newsapp.ui.home.component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.omaradev.newsapp.ui.util.shimmerEffect

@SuppressLint("InvalidColorHexValue")
@Composable
fun ArticleShimmer() {
    Column(modifier = Modifier.padding(top = 8.dp)) {
        ArticleShimmerItem()
        ArticleShimmerItem()
        ArticleShimmerItem()
        ArticleShimmerItem()
        ArticleShimmerItem()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleShimmerItem() {
    Card(
        modifier = Modifier
            .padding(start = 4.dp, end = 4.dp, bottom = 8.dp)
            .height(220.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .shimmerEffect(),
        )
    }
}