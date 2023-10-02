package com.omaradev.data.dto.news

import androidx.room.Entity
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class SourceNetwork(
    val name: String? = null
)
