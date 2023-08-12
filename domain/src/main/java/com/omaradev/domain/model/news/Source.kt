package com.omaradev.domain.model.news

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Source(
    val name: String?=null
) : Parcelable