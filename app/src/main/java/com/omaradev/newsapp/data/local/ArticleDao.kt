package com.omaradev.newsapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.omaradev.newsapp.domain.model.news.Article

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(articles: Article)

    @Query("SELECT * FROM AllNews")
    suspend fun getAllArticle(): List<Article>

    @Query("DELETE FROM AllNews")
    suspend fun clearTable()

}