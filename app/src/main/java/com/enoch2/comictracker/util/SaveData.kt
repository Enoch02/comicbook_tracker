package com.enoch2.comictracker.util

import android.content.Context
import android.util.Log
import com.enoch2.comictracker.Comic
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

const val FILE_NAME = "data.json"

// TODO: is this function necessary?
fun saveData(
    context: Context, title: String, status: String,
    rating: Int, issuesRead: String, totalIssues: String
) {
    val file = File(context.filesDir, FILE_NAME)
    val json = Json.encodeToString(Comic(title, status, rating, issuesRead, totalIssues))

    if (file.createNewFile()) {
        file.writeText(json)
        file.appendText("\r")
        Log.w("comic_tracker_file", "text written")
    }
    else {
        file.appendText(json)
        file.appendText("\r")
        Log.w("comic_tracker_file", "text appended")
    }
}