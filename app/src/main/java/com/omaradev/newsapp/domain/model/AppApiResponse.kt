package com.omaradev.newsapp.domain.model

data class AppApiResponse<T>(
    val articles: T? = null,
    var status: Boolean = false,
    val totalResults: Int = 0,

    //ApiErrorModel
    val message: String? = null,
    val code: String? = null,
)
