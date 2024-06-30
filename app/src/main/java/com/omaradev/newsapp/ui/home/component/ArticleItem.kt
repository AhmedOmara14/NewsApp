package com.omaradev.newsapp.ui.home.component

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.omaradev.newsapp.R
import com.omaradev.domain.model.news.Article

@SuppressLint("InvalidColorHexValue")
@Composable
fun ArticleItem(article: Article, onClickArticle: (serializableArticle: Article) -> Unit) {
    Card(
        elevation = 10.dp,
        modifier = Modifier
            .padding(start = 4.dp, end = 4.dp, bottom = 8.dp)
            .fillMaxWidth()
            .height(220.dp).clickable {
                onClickArticle(article)
            }
    ) {
        Box{
            Image(
                painter = rememberAsyncImagePainter(article.urlToImage),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0x75000000),
                                Color(0x1D000000),
                                Color(0x14FFFFFF),
                            )
                        )
                    )
                    .padding(4.dp)
            ) {
                article.source.name?.let {
                    Text(
                        text = it,
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.cairoregular))
                        )
                    )
                }
                article.description?.let {
                    Text(
                        text = it,
                        style = TextStyle(
                            color = Color(0xFFFFFFFF),
                            fontSize = 14.sp,
                            fontFamily = FontFamily(
                                Font(R.font.cairoregular)
                            )
                        ), maxLines = 1,
                        overflow = TextOverflow.Ellipsis

                    )
                }
            }
        }
    }
}