package com.enoch2.comictracker.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.enoch2.comictracker.ui.composables.ComicInputLayout
import com.enoch2.comictracker.util.ComicInputMode

@Composable
fun EditComicScreen(
    navController: NavController,
    comicTitle: String?,
    status: String?,
    rating: Float?,
    issuesRead: String?,
    totalIssues: String?
) {
    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Edit Comic") },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        content = { Icon(Icons.Default.ArrowBack, "back") })
                }
            )
        },
        content = {
            Surface(modifier = Modifier.fillMaxSize()) {
                ComicInputLayout(
                    comicTitle = comicTitle.toString(),
                    selectedStatus = status.toString(),
                    rating = rating!!,
                    issuesRead = issuesRead.toString(),
                    totalIssues = totalIssues.toString(),
                    mode = ComicInputMode.EDIT
                )
            }
        }
    )
}
