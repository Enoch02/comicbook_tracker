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
import com.enoch2.comictracker.ui.composables.FilterLabel
import com.enoch2.comictracker.util.Filters
import com.enoch2.comictracker.util.OrderType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// TODO: search functionality
@Composable
private fun TopAppBar(
    navController: NavController,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    onOrderMenuItemClick: () -> Unit
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
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = !showMenu }
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                showInnerMenu = !showInnerMenu
                                showMenu = !showMenu
                            },
                            content = { Text(stringResource(R.string.sort)) }
                        )
                        Divider()
                        DropdownMenuItem(
                            onClick = { navController.navigate(Screen.SettingScreen.route) },
                            content = { Text(stringResource(R.string.settings)) }
                        )
                    }
                    // for changing sort order
                    DropdownMenu(
                        expanded = showInnerMenu,
                        onDismissRequest = {
                            showInnerMenu = !showInnerMenu
                            showMenu = !showMenu
                        }
                    ) {
                        //TODO: str resource
                        DropdownMenuItem(
                            onClick = onOrderMenuItemClick,
                            content = { Text("Descending") }
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
    val drawerState = scaffoldState.drawerState
    var filter by remember { mutableStateOf(Filters.ALL) }
    var order by remember { mutableStateOf(OrderType.ASCENDING) }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopAppBar(
            navController,
            scaffoldState,
            scope,
            onOrderMenuItemClick = { order = OrderType.DESCENDING }
        ) },
        drawerContent = {
            DrawerHeader()
            Divider()
            val drawerItems = listOf(
                stringResource(R.string.reading), stringResource(R.string.completed),
                stringResource(R.string.on_hold), stringResource(R.string.dropped)
            )

            FilterLabel()
            drawerItems.forEachIndexed { index, item ->
                TextButton(
                    modifier = Modifier.fillMaxWidth(),
                    content = { Text(item) },
                    onClick = {
                        when (index) {
                            0 -> {
                                //TODO: automatically close drawer
                                filter = Filters.READING
                                scope.launch {
                                    drawerState.close()
                                }
                            }
                            1 -> {
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
        //val comics = viewModel.comics.collectAsState(initial = emptyList()).value
        val comics = viewModel.getComics(filter, order).collectAsState(initial = emptyList()).value

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
