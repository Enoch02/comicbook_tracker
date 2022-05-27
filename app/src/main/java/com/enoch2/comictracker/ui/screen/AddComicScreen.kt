package com.enoch2.comictracker.ui.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.enoch2.comictracker.R
import com.enoch2.comictracker.domain.model.ComicTrackerViewModel
import com.enoch2.comictracker.domain.model.ComicTrackerViewModelFactory
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
