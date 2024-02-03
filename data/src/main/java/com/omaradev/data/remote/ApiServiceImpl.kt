package com.omaradev.data.remote

import com.omaradev.data.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

class ApiServiceImpl(
    private val client: HttpClient
) : ApiService {
    override suspend fun getAllArticles(
        page: Int, pageSize: Int, language: String, apiKey: String, searchValue: String
    ): HttpResponse {
        val response = client.get(
            BuildConfig.BASE_URL + BuildConfig.API_URL + "everything"
        ) {
            url {
                parameters.append("page", page.toString())
                parameters.append("pageSize", pageSize.toString())
                parameters.append("language", language)
                parameters.append("apiKey", apiKey)
                parameters.append("q", searchValue)
            }
        }
        return response
    }

}