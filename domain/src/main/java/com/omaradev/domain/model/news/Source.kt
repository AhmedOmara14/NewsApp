package com.omaradev.domain.model.news

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Source(
    val name: String?=null
) : Parcelable