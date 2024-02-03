package com.omaradev.data.di

import com.omaradev.data.local.ArticleDB
import com.omaradev.data.remote.ApiService
import com.omaradev.data.repository.RepositoryImpl
import com.omaradev.domain.repository.Repository
import org.koin.dsl.module

object RepositoryModule {
    val RepositoryModule = module {
        single<Repository> { provideRepository(get(), get()) }
    }

    private fun provideRepository(api: ApiService, db: ArticleDB): Repository {
        return RepositoryImpl(api, db)
    }

}