/*
    Contains everything needed to load files from storage
 */
package com.enoch2.comictracker.util

import android.content.Context
import android.util.Log
import com.enoch2.comictracker.Comic
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.File


fun loadData(context: Context): MutableList<Comic> {
    val file = File(context.filesDir, FILE_NAME)
    val comics = mutableListOf<Comic>()

    if (file.exists()) {
        file.forEachLine {
            val obj = Json.decodeFromString<Comic>(it)
            comics.add(obj)
        }
        Log.w("comic_tracker_file", "File exists")
    }
    else {
        Log.w("comic_tracker_file", "File does not exist")
    }
    return comics
}