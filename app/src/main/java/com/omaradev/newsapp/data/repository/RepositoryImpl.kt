package com.omaradev.newsapp.data.repository

import com.omaradev.newsapp.data.local.ArticleDB
import com.omaradev.newsapp.data.remote.ApiService
import com.omaradev.newsapp.domain.model.news.Article
import com.omaradev.newsapp.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(val api: ApiService, private val db: ArticleDB) :
    Repository,
    AppBaseRemoteRepository() {
    override fun getAllArticlesByTitle(
        page: Int,
        pageSize: Int,
        language: String,
        apiKey: String,
        searchValue: String
    ) = makeApiRequest({
        api.getAllArticlesByTitle(
            page, pageSize, language, apiKey, searchValue
        )
    })

    override suspend fun insertArticle(article: Article) = flow {
        try {
            db.articleDao.insert(article)
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

    override suspend fun getAllArticles(): Flow<RemoteRequestStatus<List<Article>>> = flow {
        try {
            val allArticles = db.articleDao.getAllArticle()
            emit(RemoteRequestStatus.OnSuccessRequest<List<Article>>(allArticles))
        } catch (e: HttpException) {
            emit(
                RemoteRequestStatus.OnFailedRequest<List<Article>>(
                    responseBody = null,
                    e.localizedMessage ?: "an Error Occurred"
                )
            )
        } catch (e: IOException) {
            emit(
                RemoteRequestStatus.OnFailedRequest<List<Article>>(
                    responseBody = null,
                    "No Internet Connection, Check your Internet"
                )
            )
        }
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