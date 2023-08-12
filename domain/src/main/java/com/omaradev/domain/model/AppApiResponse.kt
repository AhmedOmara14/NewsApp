package com.omaradev.domain.model

data class AppApiResponse<T>(
    val articles: T? = null,
    var status: String ,
    val totalResults: Int = 0,

    //ApiErrorModel
    val message: String? = null,
    val code: String? = null,
)
