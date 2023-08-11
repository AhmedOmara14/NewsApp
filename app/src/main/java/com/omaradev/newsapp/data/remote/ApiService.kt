package com.omaradev.newsapp.data.remote

import com.omaradev.newsapp.BuildConfig
import com.omaradev.newsapp.domain.model.AppApiResponse
import com.omaradev.newsapp.domain.model.news.Article
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("everything")
    suspend fun getAllArticlesByTitle(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("language") language: String,
        @Query("apiKey") apiKey: String,
        @Query("q") searchValue: String
    ): Response<AppApiResponse<List<Article>>>
}