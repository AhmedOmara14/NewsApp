package com.omaradev.data.repository

import com.omaradev.data.dto.AppApiResponseNetwork
import com.omaradev.data.dto.news.ArticleNetwork
import com.omaradev.data.dto.news.toArticle
import com.omaradev.data.dto.news.toArticleNetwork
import com.omaradev.data.local.ArticleDB
import com.omaradev.data.remote.ApiService
import com.omaradev.domain.model.AppApiResponse
import com.omaradev.domain.model.news.Article
import com.omaradev.domain.repository.RemoteRequestStatus
import com.omaradev.domain.repository.Repository
import io.ktor.client.call.body
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json

class RepositoryImpl(val api: ApiService, private val db: ArticleDB) :
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
                val response = api.getAllArticles(
                    page, pageSize, language, apiKey, searchValue
                )
                if (response.status.isSuccess()) {
                    val responseBody = response.body<String>()
                    val articleNetworkList = Json {
                        ignoreUnknownKeys = true
                    }.decodeFromString<AppApiResponseNetwork<List<ArticleNetwork>>>(responseBody)
                    val articleList = articleNetworkList.articles?.map { it.toArticle() }
                    emit(
                        RemoteRequestStatus.OnSuccessRequest(
                            AppApiResponse(
                                status = articleNetworkList.status,
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

    override fun deleteAllArticles() = flow {
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