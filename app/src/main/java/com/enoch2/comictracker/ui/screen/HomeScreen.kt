package com.enoch2.comictracker.ui.screen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
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
import com.enoch2.comictracker.ui.composables.FilterLabel
import com.enoch2.comictracker.ui.composables.HomeScreenDropDownMenu
import com.enoch2.comictracker.ui.theme.White
import com.enoch2.comictracker.util.Filters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// TODO: search functionality
@Composable
fun HomeScreen(
    navController: NavController,
    context: Context,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    listState: LazyListState
) {
    val drawerState = scaffoldState.drawerState
    val viewModel: ComicTrackerViewModel = viewModel(
        factory = ComicTrackerViewModelFactory(context.applicationContext)
    )
    val comics by viewModel.getComics(viewModel.filter, viewModel.order)
        .collectAsState(initial = emptyList())

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(content = {
                        Icon(
                            Icons.Default.Menu,
                            tint = White,
                            contentDescription = stringResource(R.string.menu)
                        )
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
                elevation = 4.dp,
                actions = {
                    if (viewModel.selectedIds.isNotEmpty()) {
                        IconButton(
                            onClick = {
                                viewModel.deleteSelectedIds()
                            },
                            content = {
                                Icon(Icons.Default.Delete, stringResource(R.string.delete))
                            }
                        )
                    }

                    HomeScreenDropDownMenu(context, navController, comics.map { it.id!! })
                }
            )
        },
        drawerContent = {
            DrawerHeader()
            Divider()
            val drawerItems = listOf(
                stringResource(R.string.reading), stringResource(R.string.completed),
                stringResource(R.string.on_hold), stringResource(R.string.dropped),
                stringResource(R.string.plan_to_read), stringResource(R.string.all)
            )

            FilterLabel()
            drawerItems.forEachIndexed { index, item ->
                TextButton(
                    modifier = Modifier.fillMaxWidth(),
                    content = { Text(item) },
                    onClick = {
                        when (index) {
                            0 -> {
                                viewModel.filter = Filters.READING
                                scope.launch {
                                    drawerState.close()
                                }
                            }
                            1 -> {
                                viewModel.filter = Filters.COMPLETED
                                scope.launch {
                                    drawerState.close()
                                }
                            }
                            2 -> {
                                viewModel.filter = Filters.ON_HOLD
                                scope.launch {
                                    drawerState.close()
                                }
                            }
                            3 -> {
                                viewModel.filter = Filters.DROPPED
                                scope.launch {
                                    drawerState.close()
                                }
                            }
                            4 -> {
                                viewModel.filter = Filters.PLAN_TO_READ
                                scope.launch {
                                    drawerState.close()
                                }
                            }
                            5 -> {
                                viewModel.filter = Filters.ALL
                                scope.launch {
                                    drawerState.close()
                                }
                            }
                        }
                    }
                )
            }
        },
        drawerGesturesEnabled = true,
        floatingActionButton = {
            if (viewModel.selectedIds.isEmpty()) {
                FloatingActionButton(
                    onClick = { navController.navigate(Screen.AddComicScreen.route) },
                    content = {
                        Icon(
                            Icons.Default.Add,
                            "add",
                            tint = White
                        )
                    }
                )
            }
        }
    ) {
        val coverPaths by viewModel.coverPaths.collectAsState(initial = emptyMap())

        LazyColumn(state = listState, contentPadding = PaddingValues(10.dp)) {
            items(
                count = comics.size,
                itemContent = { index ->
                    val comic = comics[index]
                    val bg = if (viewModel.selectedIds.contains(comic.id)) {
                        Color.Blue.copy(alpha = 0.5f)
                    } else Color.Transparent

                    Card(
                        elevation = 4.dp,
                        modifier = Modifier.padding(bottom = 10.dp)
                    ) {
                        ComicInfoLayout(
                            comicTitle = comic.title.toString(),
                            issuesRead = comic.issuesRead!!,
                            totalIssues = comic.totalIssues!!,
                            status = comic.status.toString(),
                            coverAbsPath = coverPaths[comic.coverName.toString()],
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Max)
                                .background(bg)
                                .pointerInput(comic) {
                                    detectTapGestures(
                                        onTap = {
                                            // is a comic entry selected?
                                            if (viewModel.selectedIds.isEmpty() && comics.contains(
                                                    comic
                                                )
                                            ) {
                                                navController.navigate(
                                                    Screen.ComicDetailScreen.withArgs(comic.id.toString())
                                                )
                                            } else {
                                                if (viewModel.selectedIds.contains(comic.id!!)) {
                                                    viewModel.deSelectId(comic.id)
                                                } else if (comics.contains(comic)) {
                                                    viewModel.selectId(comic.id)
                                                }
                                            }
                                        },
                                        onLongPress = {
                                            if (viewModel.selectedIds.isEmpty()) {
                                                viewModel.selectId(comic.id!!)
                                            }
                                        }
                                    )
                                }
                        )
                    }
                }
            )
        }
    }
}
