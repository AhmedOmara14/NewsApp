package com.omaradev.data.di

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.omaradev.data.local.ArticleDB
import com.omaradev.data.remote.ApiService
import com.omaradev.data.remote.ApiServiceImpl
import com.omaradev.data.repository.RepositoryImpl
import com.omaradev.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.headers
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideKtorClient(): HttpClient {
        return HttpClient(Android) {
            expectSuccess = true
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.v("HTTP Client", message)
                    }
                }
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
                headers {
                    headersOf("Content-Type", "application/json")
                }
            }
        }
    }


    @Singleton
    @Provides
    fun provideApiService(client: HttpClient): ApiService {
        return ApiServiceImpl(client)
    }

    @Singleton
    @Provides
    fun provideRepository(api: ApiService, db: ArticleDB): Repository {
        return RepositoryImpl(api, db)
    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application): ArticleDB {
        return Room.databaseBuilder(
            app,
            ArticleDB::class.java,
            ArticleDB.DATABASE_NAME
        ).allowMainThreadQueries().build()
    }
}