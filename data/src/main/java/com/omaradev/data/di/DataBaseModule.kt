package com.omaradev.data.di

import android.app.Application
import androidx.room.Room
import com.omaradev.data.local.ArticleDB
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

object DataBaseModule {
    val DataBaseModule = module {
        single<ArticleDB> { provideDatabase(androidApplication()) }
    }

    private fun provideDatabase(app: Application): ArticleDB {
        return Room.databaseBuilder(
            app,
            ArticleDB::class.java,
            ArticleDB.DATABASE_NAME
        ).allowMainThreadQueries().build()
    }

}