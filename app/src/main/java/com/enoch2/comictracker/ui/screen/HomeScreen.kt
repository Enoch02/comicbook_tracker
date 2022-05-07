package com.enoch2.comictracker.ui.screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
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
import com.enoch2.comictracker.R
import com.enoch2.comictracker.data.Comic
import com.enoch2.comictracker.ui.layouts.ComicInfoLayout
import com.enoch2.comictracker.ui.layouts.DrawerLayout
import com.enoch2.comictracker.router.Router.navigateTo
import com.enoch2.comictracker.router.Screen
import com.enoch2.comictracker.ui.theme.BlueGrayDark
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeTopAppBar(scaffoldState: ScaffoldState, scope: CoroutineScope) {
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
                    navigateTo(Screen.SettingScreen)
                },
                content = { Icon(
                    Icons.Default.Settings,
                    stringResource(R.string.settings),
                    tint = tint
                ) }
            )
        }
    )
}

@Composable
fun HomeScreen(
    context: Context,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    listState: LazyListState
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { HomeTopAppBar(scaffoldState = scaffoldState, scope = scope) },
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
                content = { DrawerLayout(context) }
            )
        },
        drawerGesturesEnabled = true,
        floatingActionButton = { FloatingActionButton(onClick = { navigateTo(Screen.AddComicScreen) }) {
            Icon(
                Icons.Default.Add,
                contentDescription = "add",
                tint = Color.White
            )
            }
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // TODO: load with coroutine
            val comics = Comic.loadComics(context)

            //TODO: figure out how to send selected comic to its details screen
            LazyColumn(state = listState) {
                items(comics) { comic ->
                    Card(
                        elevation = 2.dp,
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
                            .clickable {
                                Toast
                                    .makeText(context, comic.title, Toast.LENGTH_SHORT)
                                    .show()
                            }
                    ) {
                        ComicInfoLayout(comicTitle = comic.title, issuesRead = comic.issuesRead ,
                            totalIssues = comic.totalIssues, status = comic.status)
                    }
                }
            }
        }
    }
}
