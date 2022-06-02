package com.enoch2.comictracker.ui.screen

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.enoch2.comictracker.R
import com.enoch2.comictracker.ui.composables.ComicInputLayout
import com.enoch2.comictracker.util.ComicInputMode

@Composable
fun AddComicScreen(
    navController: NavController,
    context: Context
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.add_new_comic_title)) },
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
                    navController = navController,
                    context = context,
                    comicTitle = "",
                    selectedStatus = "reading",
                    rating = 0f,
                    issuesRead = "",
                    totalIssues = "",
                    mode = ComicInputMode.ADD
                )
            }
        }
    )
}
