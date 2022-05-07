package com.enoch2.comictracker.data

import android.content.Context
import android.widget.Toast
import com.enoch2.comictracker.router.Router
import com.enoch2.comictracker.router.Screen
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
        }

        fun loadComics(context: Context): MutableList<Comic> {
            val file = File(context.filesDir, FILE_NAME)
            val comics = mutableListOf<Comic>()

            if (file.exists()) {
                file.forEachLine {
                    val obj = Json.decodeFromString<Comic>(it)
                    comics.add(obj)
                }
            }
            return comics
        }

        /*Save input if valid*/
        fun validateInput(
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
                        Router.navigateTo(Screen.HomeScreen)
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
                        Router.navigateTo(Screen.HomeScreen)
                        Toast.makeText(context, "Comic Saved!", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Only whole numbers are allowed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
