package com.omaradev.newsapp.data.repository

import com.omaradev.domain.model.AppApiResponse
import com.omaradev.domain.model.news.Article
import com.omaradev.domain.repository.RemoteRequestStatus
import com.omaradev.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class FakeRepository : Repository {
    private var allSavedArticles = mutableListOf<Article>()

    override fun getAllArticlesByTitle(
        page: Int,
        pageSize: Int,
        language: String,
        apiKey: String,
        searchValue: String
    ): Flow<RemoteRequestStatus<AppApiResponse<List<Article>>>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertArticle(article: Article) {
        allSavedArticles.add(article)
    }

    override suspend fun getAllArticles(): List<Article> {
        return allSavedArticles
    }

    override fun deleteAllArticles(): Flow<RemoteRequestStatus<Any>> = flow {
        allSavedArticles.clear()
        emit(RemoteRequestStatus.OnSuccessRequest<Any>("Success"))
    }


}