package com.omaradev.newsapp.domain.repository

import com.omaradev.newsapp.data.repository.RemoteRequestStatus
import com.omaradev.newsapp.domain.model.AppApiResponse
import com.omaradev.newsapp.domain.model.news.Article
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getAllArticlesByTitle(
        page: Int,
        pageSize: Int,
        language: String,
        apiKey: String,
        searchValue: String
    ): Flow<RemoteRequestStatus<AppApiResponse<List<Article>>>>

}