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
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.ArrowRight
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
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
import com.enoch2.comictracker.ui.composables.FilterLabel
import com.enoch2.comictracker.util.Filters
import com.enoch2.comictracker.util.OrderType
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
    var filter by remember { mutableStateOf(Filters.ALL) }
    var order by remember { mutableStateOf(OrderType.ASCENDING) }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            val tint = Color.White

            TopAppBar(
                navigationIcon = {
                    IconButton(content = {
                        Icon(
                            Icons.Default.Menu,
                            tint = tint,
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
                actions = {
                    var showMenu by remember { mutableStateOf(false) }
                    var showInnerMenu by remember { mutableStateOf(false) }

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

                            val items = listOf(
                                stringResource(R.string.sort),
                                stringResource(R.string.settings)
                            )
                            DropdownMenu(
                                expanded = showMenu,
                                onDismissRequest = { showMenu = !showMenu },
                                content = {
                                    items.forEachIndexed { index, item ->
                                        DropdownMenuItem(
                                            contentPadding = PaddingValues(horizontal = 15.dp),
                                            onClick = {
                                                if (index == 0) {
                                                    showInnerMenu = !showInnerMenu
                                                    showMenu = !showMenu
                                                } else {
                                                    navController.navigate(Screen.SettingScreen.route)
                                                }
                                            },
                                            content = {
                                                Box(
                                                    modifier = Modifier.fillMaxSize(),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Row {
                                                        Text(item)
                                                        if (index == 0)
                                                            Icon(Icons.Outlined.ArrowRight, null)
                                                    }
                                                }
                                            }
                                        )
                                    }
                                }
                            )
                        }
                    )
                    val innerMenuItems = listOf(
                        stringResource(R.string.ascending),
                        stringResource(R.string.descending)
                    )
                    var selected by rememberSaveable { mutableStateOf(innerMenuItems[0]) }
                    val onSelectionChange = { text: String ->
                        selected = text
                    }
                    // for changing sort order
                    DropdownMenu(
                        expanded = showInnerMenu,
                        onDismissRequest = {
                            if (showMenu) showMenu = !showMenu
                            showInnerMenu = !showInnerMenu
                        },
                        content = {
                            innerMenuItems.forEachIndexed { index, item ->
                                DropdownMenuItem(
                                    contentPadding = PaddingValues(horizontal = 15.dp),
                                    onClick = {
                                        if (index == 0) {
                                            order = OrderType.ASCENDING
                                            showInnerMenu = !showInnerMenu
                                            onSelectionChange(item)
                                        } else {
                                            order = OrderType.DESCENDING
                                            showInnerMenu = !showInnerMenu
                                            onSelectionChange(item)
                                        }
                                    }
                                ) {
                                    Row {
                                        Text(item)
                                        RadioButton(
                                            selected = item == selected,
                                            onClick = {
                                                if (index == 0) {
                                                    order = OrderType.ASCENDING
                                                    showInnerMenu = !showInnerMenu
                                                    onSelectionChange(item)
                                                } else {
                                                    order = OrderType.DESCENDING
                                                    showInnerMenu = !showInnerMenu
                                                    onSelectionChange(item)
                                                }
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    )
                },
                elevation = 4.dp
            )
        },
        drawerContent = {
            DrawerHeader()
            Divider()
            val drawerItems = listOf(
                stringResource(R.string.reading), stringResource(R.string.completed),
                stringResource(R.string.on_hold), stringResource(R.string.dropped),
                stringResource(R.string.plan_to_read)
            )

            FilterLabel()
            drawerItems.forEachIndexed { index, item ->
                TextButton(
                    modifier = Modifier.fillMaxWidth(),
                    content = { Text(item) },
                    onClick = {
                        when (index) {
                            0 -> {
                                filter = Filters.READING
                                scope.launch {
                                    drawerState.close()
                                }
                            }
                            1 -> {
                                filter = Filters.COMPLETED
                                scope.launch {
                                    drawerState.close()
                                }
                            }
                            2 -> {
                                filter = Filters.ON_HOLD
                                scope.launch {
                                    drawerState.close()
                                }
                            }
                            3 -> {
                                filter = Filters.DROPPED
                                scope.launch {
                                    drawerState.close()
                                }
                            }
                            4 -> {
                                filter = Filters.PLAN_TO_READ
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
        val comics by viewModel.getComics(filter, order).collectAsState(initial = emptyList())

        LazyColumn(state = listState, contentPadding = PaddingValues(10.dp)) {
            items(
                count = comics.size,
                itemContent = { index ->
                    val comic = comics[index]
                    Card(
                        elevation = 4.dp,
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
