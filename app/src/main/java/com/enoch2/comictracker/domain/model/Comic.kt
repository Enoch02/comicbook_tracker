package com.enoch2.comictracker.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Comic(
    val title: String = "",
    val status: String = "",
    val rating: Int = 0,
    @ColumnInfo(name = "issues_read")val issuesRead: Int = 0,
    @ColumnInfo(name = "total_issues")val totalIssues: Int = 0,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)