package com.enoch2.comictracker.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.enoch2.comictracker.R
import com.enoch2.comictracker.data.Comic
import com.enoch2.comictracker.ui.common_composables.ComicTrackerTopBar

@Composable
fun ComicDetailScreen(navController: NavController, comicTitle: String?) {
    Scaffold(
        topBar = {
            ComicTrackerTopBar(
            title = stringResource(R.string.comic_detail),
            navIcon = Icons.Default.ArrowBack,
            contentDescription = stringResource(R.string.back),
            onClick = { navController.popBackStack() }
            )
        },
        content = {

        }
    )
}