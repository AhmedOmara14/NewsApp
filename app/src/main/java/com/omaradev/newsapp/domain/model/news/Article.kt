package com.omaradev.newsapp.domain.model.news

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.annotation.Nullable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
data class Article(
    val author: String?=null,
    val content: String?=null,
    val description: String?=null,
    val publishedAt: String?=null,
    val source: Source?=null,
    val title: String?=null,
    val url: String?=null,
    val urlToImage: String?=null
) : Parcelable