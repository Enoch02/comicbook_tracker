package com.enoch2.comictracker.ui.screen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.enoch2.comictracker.R
import com.enoch2.comictracker.model.ComicTrackerViewModel
import com.enoch2.comictracker.navigation.Screen
import com.enoch2.comictracker.ui.layouts.ComicInfoLayout
import com.enoch2.comictracker.ui.layouts.DrawerLayout
import com.enoch2.comictracker.ui.theme.BlueGrayDark
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

//TODO: update dependencies
@Composable
fun MainTopAppBar(navController: NavController, scaffoldState: ScaffoldState, scope: CoroutineScope) {
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
    listState: LazyListState,
    comicTrackerViewModel: ComicTrackerViewModel = viewModel()
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { MainTopAppBar(navController, scaffoldState, scope) },
        drawerContent = {
            Box(
                contentAlignment = Alignment.TopStart,
                content = {
                    Text(
                        stringResource(R.string.app_name),
                        fontFamily = FontFamily.Monospace,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .background(BlueGrayDark)
            )
            Column(
                modifier = Modifier.weight(3f),
                content = { DrawerLayout(navController, context) }
            )
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
        LazyColumn(state = listState, contentPadding = PaddingValues(10.dp)) {
            items(
                count = comicTrackerViewModel.comics.size,
                itemContent = { index ->
                    val comic = comicTrackerViewModel.comics[index]
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
