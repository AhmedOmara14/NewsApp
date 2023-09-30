package com.omaradev.data.local

import androidx.room.*
import com.omaradev.data.dto.news.TypeConverter
import com.omaradev.data.dto.news.ArticleNetwork

@Database(entities = [ArticleNetwork::class], version = 1, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class ArticleDB : RoomDatabase() {
    abstract val articleDao: ArticleDao
    companion object {
        const val DATABASE_NAME = "Article_db3"
    }
}