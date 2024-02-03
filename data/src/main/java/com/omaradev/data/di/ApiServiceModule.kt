package com.omaradev.data.di

import com.omaradev.data.remote.ApiService
import com.omaradev.data.remote.ApiServiceImpl
import io.ktor.client.HttpClient
import org.koin.dsl.module

object ApiServiceModule {
    val ApiServiceModule = module {
        single<ApiService> { provideApiService(get()) }
    }

    private fun provideApiService(client: HttpClient): ApiService {
        return ApiServiceImpl(client)
    }
}