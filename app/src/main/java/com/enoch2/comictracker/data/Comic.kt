package com.enoch2.comictracker.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

const val FILE_NAME = "data.json"

@Serializable
data class Comic(
    val title: String,
    val status: String,
    val rating: Int,
    val issuesRead: String,
    val totalIssues: String) {

    companion object {
        private val comics = mutableListOf<Comic>()

        private fun saveComic(
            context: Context,
            title: String,
            status: String,
            rating: Int,
            issuesRead: String,
            totalIssues: String
        ) {
            val file = File(context.filesDir, FILE_NAME)
            val json = Json.encodeToString(Comic(title, status, rating, issuesRead, totalIssues))

            if (file.createNewFile()) {
                file.writeText(json)
                file.appendText("\r")
            }
            else {
                file.appendText(json)
                file.appendText("\r")
            }
            Log.w("file_io", "file saved")
        }

        fun loadComics(context: Context, scope: CoroutineScope): List<Comic> {
            val file = File(context.filesDir, FILE_NAME)
            scope.launch {
                if (file.exists()) {
                    file.forEachLine {
                        val obj = Json.decodeFromString<Comic>(it)
                        comics.add(obj)
                    }
                }
            }
            Log.w("file_io", "file loaded")
            return comics.toList()
        }

        /*Save input if valid*/
        fun validateInput(
            navController: NavController,
            context: Context,
            comicTitle: String,
            issuesRead: String,
            totalIssues: String,
            rating: Int,
            selectedStatus: String, ) {
            try {
                when {
                    comicTitle == "" -> {
                        Toast.makeText(context, "Add a comic title", Toast.LENGTH_SHORT).show()
                    }
                    issuesRead == "" && totalIssues == "" -> {
                        saveComic(
                            context, comicTitle, selectedStatus,
                            rating, "0", "0"
                        )
                        navController.popBackStack()
                        Toast.makeText(context, "Comic Saved!", Toast.LENGTH_SHORT).show()
                    }
                    issuesRead.toInt() > totalIssues.toInt() -> {
                        Toast.makeText(
                            context,
                            "Issues read should not be greater than total issues",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> {
                        saveComic(
                            context, comicTitle, selectedStatus,
                            rating, issuesRead, totalIssues
                        )
                        navController.popBackStack()
                        Toast.makeText(context, "Comic Saved!", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Only whole numbers are allowed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
