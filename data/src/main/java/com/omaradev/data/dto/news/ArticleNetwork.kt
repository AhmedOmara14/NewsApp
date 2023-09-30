package com.omaradev.data.dto.news

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.omaradev.domain.model.news.Article
import com.omaradev.domain.model.news.Source

@Entity(tableName = "AllNews")
data class ArticleNetwork(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    var id: Int = 0,
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    @Embedded
    val source: SourceNetwork,
    val title: String? = null,
    val url: String? = null,
    val urlToImage: String? = null
)

fun Article.toArticleNetwork(): ArticleNetwork {
    return ArticleNetwork(
        id = id,
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = SourceNetwork(source.name),
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}

fun ArticleNetwork.toArticle(): Article {
    return Article(
        id = id,
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = Source(source.name),
        title = title,
        url = url,
        urlToImage = urlToImage,
    )
}

