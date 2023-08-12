package com.omaradev.data.repository

import com.omaradev.data.local.ArticleDB
import com.omaradev.data.remote.ApiService
import com.omaradev.domain.model.news.Article
import com.omaradev.domain.repository.Repository
import kotlinx.coroutines.flow.flow
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

    override suspend fun insertArticle(article: Article) {
        db.articleDao.insert(article)
    }

    override suspend fun getAllArticles(): List<Article> {
        return db.articleDao.getAllArticle()
    }

    override suspend fun deleteAllArticles() = flow {
        try {
            db.articleDao.clearTable()
            emit(com.omaradev.domain.repository.RemoteRequestStatus.OnSuccessRequest<Any>("Success"))
        } catch (e: Exception) {
            emit(
                com.omaradev.domain.repository.RemoteRequestStatus.OnFailedRequest<Any>(
                    responseBody = null,
                    errorMessage = e.localizedMessage ?: "an Error Occurred"
                )
            )
        }
    }
}