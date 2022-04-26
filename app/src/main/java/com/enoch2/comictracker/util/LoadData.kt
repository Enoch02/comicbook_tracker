/*
    Contains everything needed to load files from storage
 */
package com.enoch2.comictracker.util

import android.content.Context
import android.util.Log
import java.io.File


fun loadData(context: Context) {
    val file = File(context.filesDir, FILE_NAME)

    if (file.exists()) {
        file.forEachLine {
            // TODO: decode json files and return a list of the objects
        }
        Log.w("file_operation", "File exists")
    }
    else {
        Log.w("file_operation", "File does not exist")
    }
}