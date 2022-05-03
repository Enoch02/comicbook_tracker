package com.enoch2.comictracker.app

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.enoch2.comictracker.R
import com.enoch2.comictracker.router.Router.navigateTo
import com.enoch2.comictracker.router.Screen
import com.enoch2.comictracker.ui.theme.BlueGrayDark
import com.enoch2.comictracker.util.loadData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeTopAppBar(scaffoldState: ScaffoldState, scope: CoroutineScope) {
    val drawerState = scaffoldState.drawerState

    TopAppBar(
        navigationIcon = {
            IconButton(content = {
                Icon(Icons.Default.Menu,
                    tint = Color.White,
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
        title = { Text(text = stringResource(id = R.string.app_name)) },
        actions = {
            IconButton(
                onClick = {
                    navigateTo(Screen.SettingScreen)
                },
                content = { Icon(Icons.Default.Settings, stringResource(R.string.settings)) }
            )
        }
    )
}

@Composable
fun HomeScreen(
    context: Context,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    listState: LazyListState) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { HomeTopAppBar(scaffoldState = scaffoldState, scope = scope) },
        drawerContent = {
            Column(modifier = Modifier
                .weight(1f)
                .background(BlueGrayDark)) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 20.dp))
            }
            Column(modifier = Modifier.weight(3f)) {
                Drawer(context)
            }
        },
        drawerGesturesEnabled = true,
        floatingActionButton = { FloatingActionButton(onClick = { navigateTo(Screen.AddComicScreen) }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "add")
            }
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            HomeScreenContent(context, listState)
        }
    }
}

@Composable
private fun Drawer(context: Context) {
    val drawerItems = listOf(stringResource(R.string.reading),
        stringResource(R.string.completed), stringResource(R.string.on_hold),
        stringResource(R.string.dropped), stringResource(R.string.about))

    Column(modifier = Modifier.padding(10.dp)) {
        drawerItems.forEachIndexed { index, item ->
            TextButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    when (index) {
                        0 -> {
                            Toast
                                .makeText(context, "Reading!", Toast.LENGTH_SHORT)
                                .show()
                        }
                        1 -> {
                            Toast
                                .makeText(context, "Completed!", Toast.LENGTH_SHORT)
                                .show()
                        }
                        2 -> {

                        }
                        3 -> {

                        }
                        4 -> {
                            navigateTo(Screen.AboutScreen)
                        }
                    }
                }
            ){ Text(item) }

            if (index != 4) {
                Divider(
                    thickness = 0.5.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
            }
        }
    }
}

@Composable
fun HomeScreenContent(context: Context, listState: LazyListState) {
    val comics = loadData(context)

    //TODO: figure out how to send selected comic to its details screen
    LazyColumn(state = listState) {
        items(comics) { comic ->
            Card(
                elevation = 2.dp,
                modifier = Modifier.padding(10.dp)
            ) {
                ComicInfoLayout(comicTitle = comic.title, issuesRead = comic.issuesRead ,
                    totalIssues = comic.totalIssues , status = comic.status)
            }
        }
    }
}

@Composable
fun ComicInfoLayout(comicTitle: String, issuesRead: String, totalIssues: String, status: String) {
    val constraints = ConstraintSet {
        val cover = createRefFor("cover")
        val title = createRefFor("title")
        val divider = createRefFor("divider")
        val progress = createRefFor("issueInfo")
        val statusC = createRefFor("status")

        constrain(cover) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
        }
        constrain(title) {
            top.linkTo(parent.top, 5.dp)
            start.linkTo(cover.end, 20.dp)
            end.linkTo(parent.end, 20.dp)
            width = Dimension.fillToConstraints
        }
        constrain(divider) {
            top.linkTo(title.bottom, margin = 10.dp)
            bottom.linkTo(progress.top)
            start.linkTo(cover.end, 10.dp)
            end.linkTo(parent.end, 10.dp)
            width = Dimension.fillToConstraints
        }
        constrain(progress) {
            top.linkTo(divider.bottom, 10.dp)
            start.linkTo(cover.end, 20.dp)
            end.linkTo(statusC.start)
        }
        constrain(statusC) {
            top.linkTo(divider.bottom, 10.dp)
            start.linkTo(progress.end)
            end.linkTo(parent.end, 20.dp)
        }
    }

    ConstraintLayout(constraints, modifier = Modifier
        .fillMaxWidth()
        .height(85.dp)) {
        //TODO: replace with the coil version
        Image(
            painter = painterResource(R.drawable.placeholder_image),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.layoutId("cover")
        )
        Text(
            comicTitle,
            fontSize = 20.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.layoutId("title")
        )
        Divider(modifier = Modifier.layoutId("divider"))
        Text(
            "Progress: $issuesRead / $totalIssues",
            modifier = Modifier.layoutId("issueInfo")
        )
        Text(
            "Status: $status",
            modifier = Modifier.layoutId("status")
        )
    }
}
