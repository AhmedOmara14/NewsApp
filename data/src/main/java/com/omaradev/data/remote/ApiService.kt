package com.omaradev.data.remote

import io.ktor.client.statement.HttpResponse

interface ApiService {
    suspend fun getAllArticles(
        page: Int, pageSize: Int, language: String, apiKey: String, searchValue: String
    ): HttpResponse
}