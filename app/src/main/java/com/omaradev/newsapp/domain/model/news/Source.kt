package com.omaradev.newsapp.domain.model.news

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Entity
@Parcelize
data class Source(
    val name: String?=null
) : Parcelable