package com.enoch2.comictracker.ui.screen

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.enoch2.comictracker.R
import com.enoch2.comictracker.Screen
import com.enoch2.comictracker.domain.model.ComicTrackerViewModel
import com.enoch2.comictracker.domain.model.ComicTrackerViewModelFactory
import com.enoch2.comictracker.ui.composables.ComicInfoLayout
import com.enoch2.comictracker.ui.composables.DrawerHeader
import com.enoch2.comictracker.ui.composables.DrawerLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// TODO: add sort, filter and search functionality
// TODO: default filter is reading
@Composable
private fun TopAppBar(
    navController: NavController,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope
) {
    val drawerState = scaffoldState.drawerState
    val tint = Color.White
    var showMenu by remember { mutableStateOf(false) }

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
                     showMenu = !showMenu
                },
                content = {
                    Icon(
                        Icons.Default.MoreVert,
                        stringResource(R.string.settings),
                        tint = tint
                    )

                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = !showMenu }
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                //TODO:
                            },
                            content = { Text(stringResource(R.string.sort)) }
                        )
                        DropdownMenuItem(
                            onClick = { navController.navigate(Screen.SettingScreen.route) },
                            content = { Text(stringResource(R.string.settings)) }
                        )
                    }
                }
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
            DrawerLayout(context)
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
        val comics = viewModel.comics.collectAsState(initial = emptyList()).value

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
                                    navController.navigate(Screen.ComicDetailScreen.withArgs(comic.id.toString()))
                                }
                        )
                    }
                }
            )
        }
    }
}
