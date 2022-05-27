package com.enoch2.comictracker.ui.screen

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.enoch2.comictracker.R
import com.enoch2.comictracker.ui.composables.ComicInputLayout
import com.enoch2.comictracker.ui.composables.ComicTrackerTopBar
import com.enoch2.comictracker.util.ComicInputMode

@Composable
fun EditComicScreen(
    navController: NavController,
    context: Context,
    comicTitle: String?,
    status: String?,
    rating: Float?,
    issuesRead: String?,
    totalIssues: String?,
    id: String?
) {
    Scaffold (
        topBar = {
            ComicTrackerTopBar(
                title = stringResource(R.string.edit_comic),
                navIcon = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.back),
                onClick = { navController.popBackStack() }
            )
        },
        content = {
            Surface(modifier = Modifier.fillMaxSize()) {
                ComicInputLayout(
                    navController,
                    context,
                    comicTitle = comicTitle.toString(),
                    selectedStatus = status.toString(),
                    rating = rating!!,
                    issuesRead = issuesRead.toString(),
                    totalIssues = totalIssues.toString(),
                    id = id!!.toInt(),
                    ComicInputMode.EDIT
                )
            }
        }
    )
}
