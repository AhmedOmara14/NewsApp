package com.omaradev.domain.model.news

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Parcelize
data class Article(
    var id: Int,
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    val source: Source,
    val title: String? = null,
    val url: String? = null,
    val urlToImage: String? = null
) : Parcelable {
    fun convertDate(_date: String?): String? {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return try {
            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
            val date: Date = inputFormat.parse(_date) as Date
            outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            null // Handle parsing error
        }
    }
}