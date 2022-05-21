package com.enoch2.comictracker.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Comic(
    val title: String,
    val status: String,
    val rating: Int,
    @ColumnInfo(name = "issues_read")val issuesRead: Int,
    @ColumnInfo(name = "total_issues")val totalIssues: Int,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)