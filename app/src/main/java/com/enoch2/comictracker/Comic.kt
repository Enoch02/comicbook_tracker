package com.enoch2.comictracker

enum class Status {
    READING,
    COMPLETED,
    ON_HOLD,
    DROPPED,
    PLAN_TO_READ
}

data class Comic (
    val title: String,
    val status: Status,
    val rating: Int
    )