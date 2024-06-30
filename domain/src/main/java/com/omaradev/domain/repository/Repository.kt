package com.omaradev.domain.repository

import com.omaradev.domain.model.AppApiResponse
import com.omaradev.domain.model.news.Article
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getAllArticlesByTitle(
        page: Int,
        pageSize: Int,
        language: String,
        apiKey: String,
        searchValue: String
    ): Flow<RemoteRequestStatus<AppApiResponse<List<Article>>>>

    suspend fun insertArticle(article: Article)
    suspend fun getAllArticles(): List<Article>
    fun deleteAllArticles():  Flow<RemoteRequestStatus<Any>>

}