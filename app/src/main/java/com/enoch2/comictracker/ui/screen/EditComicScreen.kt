package com.enoch2.comictracker.ui.screen

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.enoch2.comictracker.R
import com.enoch2.comictracker.ui.composables.ComicInputLayout
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
    id: String?,
    coverName: String?
) {
    // TODO: Remove previous cover if it has been changed
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
        mode = ComicInputMode.EDIT,
        coverName = coverName.toString()
    )
}
