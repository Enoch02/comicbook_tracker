package com.enoch2.comictracker.util

import android.content.Context
import java.io.File

const val FILE_NAME = "data.json"

// TODO: is this function necessary?
fun saveData(context: Context) {
    val file = File(context.filesDir, FILE_NAME)

    if (file.createNewFile()) {
        file.writeText("Hello")
    }
    else {
        file.writeText("Gello")
    }
}