package com.enoch2.comictracker.ui.screen

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.enoch2.comictracker.R
import com.enoch2.comictracker.data.Comic
import com.enoch2.comictracker.model.ComicTrackerViewModel
import com.enoch2.comictracker.model.ComicTrackerViewModelFactory
import com.enoch2.comictracker.navigation.Screen
import com.enoch2.comictracker.ui.composables.ComicInfoLayout
import com.enoch2.comictracker.ui.composables.DrawerHeader
import com.enoch2.comictracker.ui.composables.DrawerLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// TODO: add sort and search functionality
@Composable
private fun TopAppBar(
    navController: NavController,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope
) {
    val drawerState = scaffoldState.drawerState
    val tint = Color.White

    TopAppBar(
        navigationIcon = {
            IconButton(content = {
                Icon(Icons.Default.Menu,
                    tint = tint,
                    contentDescription = stringResource(R.string.menu))
            },
                onClick = {
                    scope.launch {
                        if (drawerState.isClosed)
                            drawerState.open()
                        else drawerState.close()

                    }
                }
            )
        },
        title = { Text(stringResource(R.string.my_comics)) },
        actions = {
            IconButton(
                onClick = {
                    navController.navigate(Screen.SettingScreen.route)
                },
                content = { Icon(
                    Icons.Default.Settings,
                    stringResource(R.string.settings),
                    tint = tint
                ) }
            )
        },
        elevation = 1.dp
    )
}

@Composable
fun HomeScreen(
    navController: NavController,
    context: Context,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    listState: LazyListState
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopAppBar(navController, scaffoldState, scope) },
        drawerContent = {
            DrawerHeader()
            Divider()
            DrawerLayout(navController, context)
        },
        drawerGesturesEnabled = true,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.AddComicScreen.route) }
            ) {
            Icon(
                Icons.Default.Add,
                "add",
                tint = Color.White
            )
            }
        }
    ) {
        val viewModel: ComicTrackerViewModel = viewModel(
            factory = ComicTrackerViewModelFactory(context.applicationContext)
        )
        var comics by remember { mutableStateOf(listOf<Comic>()) }
        LaunchedEffect(true) {
            comics = viewModel.getAllComic()
        }

        LazyColumn(state = listState, contentPadding = PaddingValues(10.dp)) {
            items(
                count = comics.size,
                itemContent = { index ->
                    val comic = comics[index]
                    Card(
                        elevation = 2.dp,
                        modifier = Modifier.padding(bottom = 10.dp)
                    ) {
                        ComicInfoLayout(
                            comicTitle = comic.title,
                            issuesRead = comic.issuesRead,
                            totalIssues = comic.totalIssues,
                            status = comic.status,
                            Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Max)
                                .clickable {
                                    navController.navigate(Screen.ComicDetailScreen.withArgs(comic.title))
                                }
                        )
                    }
                }
            )
        }
    }
}
