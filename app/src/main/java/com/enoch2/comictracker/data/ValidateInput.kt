package com.enoch2.comictracker.data

import android.content.Context
import android.widget.Toast
import com.enoch2.comictracker.router.Router
import com.enoch2.comictracker.router.Screen

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
                saveData(
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
                saveData(
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
