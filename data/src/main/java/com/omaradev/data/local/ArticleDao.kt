package com.omaradev.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.omaradev.data.dto.news.ArticleNetwork

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(articles: ArticleNetwork)

    @Query("SELECT * FROM AllNews")
    fun getAllArticle(): List<ArticleNetwork>

    @Query("DELETE FROM AllNews")
    suspend fun clearTable()

}