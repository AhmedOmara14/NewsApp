package com.omaradev.data.di


import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NoneAuthInterceptor @Inject constructor() : Interceptor {

    private val CONTENT_TYPE = "Content-Type"
    private val CONTENT_TYPE_VALUE = "application/json"

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val authorisedRequestBuilder = originalRequest.newBuilder()
            .header(CONTENT_TYPE, CONTENT_TYPE_VALUE)
            .method(originalRequest.method, originalRequest.body)
        val response = chain.proceed(authorisedRequestBuilder.build())
        return response
    }
}
