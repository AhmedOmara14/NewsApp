package com.omaradev.data.repository

import com.omaradev.data.dto.news.toArticle
import com.omaradev.data.dto.news.toArticleNetwork
import com.omaradev.data.local.ArticleDB
import com.omaradev.data.remote.ApiService
import com.omaradev.domain.model.AppApiResponse
import com.omaradev.domain.model.news.Article
import com.omaradev.domain.repository.RemoteRequestStatus
import com.omaradev.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(val api: ApiService, private val db: ArticleDB) :
    Repository {
    override fun getAllArticlesByTitle(
        page: Int,
        pageSize: Int,
        language: String,
        apiKey: String,
        searchValue: String
    ): Flow<RemoteRequestStatus<AppApiResponse<List<Article>>>> {
        return flow {
            try {
                val response = api.getAllArticlesByTitle(
                    page, pageSize, language, apiKey, searchValue
                ).apply {
                    this.body()?.articles?.map { it.toArticle() }
                }
                if (response.isSuccessful) {
                    val articleNetworkList = response.body()?.articles ?: emptyList()
                    val articleList = articleNetworkList.map { it.toArticle() }
                    emit(
                        RemoteRequestStatus.OnSuccessRequest(
                            AppApiResponse(
                                status = response.body()?.status!!,
                                articles = articleList
                            )
                        )
                    )
                } else {
                    emit(RemoteRequestStatus.OnFailedRequest(null, "Request failed"))
                }
            } catch (e: Exception) {
                emit(
                    RemoteRequestStatus.OnFailedRequest(
                        null,
                        e.localizedMessage ?: "An error occurred"
                    )
                )
            }
        }
    }

    override suspend fun insertArticle(article: Article) {
        db.articleDao.insert(article.toArticleNetwork())
    }

    override suspend fun getAllArticles(): List<Article> {
        return db.articleDao.getAllArticle().map { it.toArticle() }
    }

    override suspend fun deleteAllArticles() = flow {
        try {
            db.articleDao.clearTable()
            emit(RemoteRequestStatus.OnSuccessRequest<Any>("Success"))
        } catch (e: Exception) {
            emit(
                RemoteRequestStatus.OnFailedRequest<Any>(
                    responseBody = null,
                    errorMessage = e.localizedMessage ?: "an Error Occurred"
                )
            )
        }
    }


}