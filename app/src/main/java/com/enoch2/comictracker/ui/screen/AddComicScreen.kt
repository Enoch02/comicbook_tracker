package com.enoch2.comictracker.ui.screen

import android.content.Context
import androidx.compose.runtime.Composable
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
    //TODO: capitalize the first letter before saving
    ComicInputLayout(
        navController = navController,
        context = context,
        topBarTitle = stringResource(R.string.add_new_comic_title),
        comicTitle = "",
        selectedStatus = "reading",
        rating = 0f,
        issuesRead = "",
        totalIssues = "",
        mode = ComicInputMode.ADD
    )
}
