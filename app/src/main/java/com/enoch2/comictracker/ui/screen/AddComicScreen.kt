package com.enoch2.comictracker.ui.screen

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Image
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
