package com.omaradev.newsapp.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.omaradev.newsapp.data.remote.ApiService
import com.omaradev.newsapp.data.repository.RepositoryImpl
import com.omaradev.newsapp.domain.repository.Repository
import com.omaradev.newsapp.BuildConfig
import com.omaradev.newsapp.data.local.ArticleDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val connectionTimeout = 30L
    private val logger: HttpLoggingInterceptor
        get() {
            val loggingInterceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                loggingInterceptor.apply {
                    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                }
            }
            return loggingInterceptor
        }


    @Provides
    @Singleton
    fun provideRetrofit(): ApiService {
        val okhttpBuilder = OkHttpClient().newBuilder().apply {
            connectTimeout(connectionTimeout, TimeUnit.SECONDS)
            readTimeout(connectionTimeout, TimeUnit.SECONDS)
            addInterceptor(logger)
            addInterceptor(NoneAuthInterceptor())
        }

        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL + BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson())).client(okhttpBuilder.build())
            .build().create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideRepository(api: ApiService,db: ArticleDB): Repository {
        return RepositoryImpl(api,db)
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