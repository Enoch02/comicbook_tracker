package com.enoch2.comictracker.ui.screen

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
    ComicInputLayout(
        navController,
        context,
        topBarTitle = stringResource(R.string.edit_comic),
        comicTitle = comicTitle.toString(),
        selectedStatus = status.toString(),
        rating = rating!!,
        issuesRead = issuesRead.toString(),
        totalIssues = totalIssues.toString(),
        id = id!!.toInt(),
        ComicInputMode.EDIT
    )
}
