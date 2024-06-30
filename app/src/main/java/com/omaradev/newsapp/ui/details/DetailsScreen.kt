package com.omaradev.newsapp.ui.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.omaradev.newsapp.R
import com.omaradev.domain.model.news.Article
import com.omaradev.newsapp.ui.home.component.LabelText
import kotlinx.coroutines.launch

@Composable
fun DetailsScreen(navController: NavHostController, article: Article?) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val intent = article?.url?.let {
        remember { Intent(Intent.ACTION_VIEW, Uri.parse(it))}
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(
                    Color.White,
                    shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                )
                .clip(shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)),
        ) {
            Image(
                painter = rememberAsyncImagePainter(article?.urlToImage),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .size(70.dp)
                    .padding(16.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable {
                        navController.navigateUp()
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .padding(6.dp)
                        .align(Alignment.Center)
                )
            }
        }

        Column(Modifier.padding(4.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                article?.source?.name?.let {
                    LabelText(label = it, modifier = Modifier)
                }
                Column {
                    article?.convertDate(article.publishedAt)?.let {
                        Text(
                            text = it,
                            style = TextStyle(
                                color = Color.Gray,
                                fontSize = 10.sp,
                                fontFamily = FontFamily(
                                    Font(R.font.cairoregular)
                                )
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                    article?.author?.let {
                        Text(
                            text = it,
                            style = TextStyle(
                                color = Color.Gray,
                                fontSize = 10.sp,
                                fontFamily = FontFamily(
                                    Font(R.font.cairobold)
                                )
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }

            article?.description?.let {
                Text(
                    text = it,
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(
                            Font(R.font.cairoregular)
                        )
                    ),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            LabelText(label = stringResource(R.string.content), modifier = Modifier)

            article?.content?.let {
                val spanned = HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY)
                val annotatedString = AnnotatedString.Builder().apply {
                    append(spanned)
                }.toAnnotatedString()
                Text(
                    text = annotatedString,
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 18.sp,
                        fontFamily = FontFamily(
                            Font(R.font.cairoregular)
                        )
                    ),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 16.dp)
                    .background(
                        color = colorResource(id = R.color.black),
                        RoundedCornerShape(16.dp)
                    )
                    .clickable {
                        coroutineScope.launch{
                            context.startActivity(intent)
                        }

                    }
            ) {
                Text(
                    text = stringResource(R.string.view_article),
                    color = Color.White,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.cairobold))
                    ),
                    modifier = Modifier.padding(
                        start = 54.dp,
                        end = 54.dp,
                        top = 8.dp,
                        bottom = 8.dp
                    )
                )
            }

        }
    }
}