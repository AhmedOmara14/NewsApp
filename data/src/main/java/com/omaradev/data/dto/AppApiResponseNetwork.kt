package com.omaradev.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AppApiResponseNetwork<T>(
    val articles: T? = null,
    var status: String ,
    val totalResults: Int = 0,

    //ApiErrorModel
    val message: String? = null,
    val code: String? = null,
)
