package com.enoch2.comictracker.ui.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.enoch2.comictracker.data.Comic
import com.enoch2.comictracker.model.ComicTrackerViewModel
import com.enoch2.comictracker.model.ComicTrackerViewModelFactory
import com.enoch2.comictracker.ui.layouts.ComicInputLayout
import kotlinx.coroutines.CoroutineScope

@Composable
fun EditComicScreen(
    navController: NavController,
    comicTitle: String?,
    context: Context,
    scope: CoroutineScope
) {
    var mComicTitle by remember { mutableStateOf("") }
    var selectedStatus by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf(0.0f) }
    var issuesRead by remember { mutableStateOf("0") }
    var totalIssues by remember { mutableStateOf("0") }

    val viewModel: ComicTrackerViewModel = viewModel(
        factory = ComicTrackerViewModelFactory(context.applicationContext)
    )
    var comic by remember { mutableStateOf(Comic("", "", 0, 0, 0)) }
    LaunchedEffect(true) {
        comic = viewModel.findComic(comicTitle!!)
    }

    ComicInputLayout(
        title = "Edit Comic",
        navIcon = {
            IconButton(
                onClick = { navController.popBackStack() },
                content = { Icon(Icons.Default.ArrowBack, "back") })
        },
        comicTitle = comic.title,
        onComicTitleChange = { value -> mComicTitle = value },
        selectedStatus = comic.status,
        onSelectedStatusChange = { selectedStatus = it } ,
        onDropdownItemClicked = { selectedStatus = it },
        rating = comic.rating.toFloat(),
        onRatingSliderChanged = { rating = it },
        issuesRead = comic.issuesRead.toString(),
        onIssuesReadChange = { issuesRead = it },
        totalIssues = comic.totalIssues.toString(),
        onTotalIssuesChange = { totalIssues = it },
        onSaveBtnClicked = {
            Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()
        }
    )
}