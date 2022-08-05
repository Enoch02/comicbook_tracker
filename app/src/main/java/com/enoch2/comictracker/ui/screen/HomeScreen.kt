package com.enoch2.comictracker.ui.screen

import android.content.Context
import android.widget.Toast
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
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.ArrowRight
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.enoch2.comictracker.ui.theme.White
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
    val viewModel: ComicTrackerViewModel = viewModel(
        factory = ComicTrackerViewModelFactory(context.applicationContext)
    )

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
                    var showMenu by remember { mutableStateOf(false) }
                    var showInnerMenu by remember { mutableStateOf(false) }

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

                    // TODO: Move to its own composable function
                    IconButton(
                        onClick = {
                            showMenu = !showMenu
                        },
                        content = {
                            Icon(
                                Icons.Default.MoreVert,
                                stringResource(R.string.menu),
                                tint = White
                            )

                            val mainMenuItems = listOf(
                                stringResource(R.string.sort),
                                stringResource(R.string.settings)
                            )
                            val selectionMenuItems = listOf(
                                stringResource(R.string.select_all),
                                stringResource(R.string.deselect_all)
                            )
                            if (viewModel.selectedIds.isEmpty()) {
                                DropdownMenu(
                                    expanded = showMenu,
                                    onDismissRequest = { showMenu = !showMenu },
                                    content = {
                                        mainMenuItems.forEachIndexed { index, item ->
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
                                                    Text(item, Modifier.weight(2f))
                                                    if (index == 0)
                                                        Icon(
                                                            Icons.Outlined.ArrowRight,
                                                            null,
                                                            Modifier.weight(1f)
                                                        )
                                                }
                                            )
                                        }
                                    }
                                )
                            } else {
                                DropdownMenu(
                                    expanded = showMenu,
                                    onDismissRequest = { showMenu = !showMenu },
                                    content = {
                                        selectionMenuItems.forEachIndexed { index, item ->
                                            DropdownMenuItem(
                                                contentPadding = PaddingValues(horizontal = 15.dp),
                                                onClick = {
                                                    if (index == 0) {
                                                        showMenu = !showMenu
                                                    } else {
                                                        showMenu = !showMenu
                                                    }
                                                },
                                                content = {
                                                    Text(item)
                                                }
                                            )
                                        }
                                    }
                                )
                            }
                        }
                    )
                    val innerMenuItems = listOf(
                        stringResource(R.string.ascending),
                        stringResource(R.string.descending),
                        stringResource(R.string.none)
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
                                        when (index) {
                                            0 -> {
                                                order = OrderType.ASCENDING
                                                showInnerMenu = !showInnerMenu
                                                onSelectionChange(item)
                                            }
                                            1 -> {
                                                order = OrderType.DESCENDING
                                                showInnerMenu = !showInnerMenu
                                                onSelectionChange(item)
                                            }
                                            else -> {
                                                order = OrderType.NONE
                                                showInnerMenu = !showInnerMenu
                                                onSelectionChange(item)
                                            }
                                        }
                                    }
                                ) {
                                    Text(item, modifier = Modifier.weight(2f))
                                    RadioButton(
                                        modifier = Modifier.weight(1f),
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
                    )
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
                            5 -> {
                                filter = Filters.ALL
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
                    tint = White
                )
            }
        }
    ) {
        val comics by viewModel.getComics(filter, order).collectAsState(initial = emptyList())
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
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onTap = {
                                            // is a comic entry selected?
                                            if (viewModel.isSelectedIdEmpty()) {
                                                navController.navigate(
                                                    Screen.ComicDetailScreen.withArgs(
                                                        comic.id.toString()
                                                    )
                                                )
                                            } else {
                                                if (viewModel.selectedIds.contains(comic.id!!)) {
                                                    viewModel.deSelectId(comic.id)
                                                    // TODO: remove
                                                    Toast
                                                        .makeText(
                                                            context,
                                                            "deselected ${comic.title}",
                                                            Toast.LENGTH_SHORT
                                                        )
                                                        .show()
                                                } else {
                                                    viewModel.selectId(comic.id)
                                                    Toast
                                                        .makeText(
                                                            context,
                                                            "selected ${comic.title}",
                                                            Toast.LENGTH_SHORT
                                                        )
                                                        .show()
                                                }
                                            }
                                        },
                                        onLongPress = {
                                            if (viewModel.isSelectedIdEmpty()) {
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
