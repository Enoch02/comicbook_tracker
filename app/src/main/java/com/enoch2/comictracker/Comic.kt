package com.enoch2.comictracker

import kotlinx.serialization.Serializable

@Serializable
data class Comic(
    val title: String,
    val status: String,
    val rating: Int,
    val issuesRead: String,
    val totalIssues: String
    )
