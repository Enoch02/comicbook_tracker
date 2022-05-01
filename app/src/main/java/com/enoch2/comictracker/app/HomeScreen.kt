package com.enoch2.comictracker.app

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.enoch2.comictracker.R
import com.enoch2.comictracker.router.Router.navigateTo
import com.enoch2.comictracker.router.Screen
import com.enoch2.comictracker.ui.theme.BlueGray100
import com.enoch2.comictracker.ui.theme.BlueGrayDark
import com.enoch2.comictracker.ui.theme.Indigo500
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
        title = { Text(text = stringResource(id = R.string.app_name)) }
    )
}

@Composable
fun HomeScreen() {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { HomeTopAppBar(scaffoldState = scaffoldState, scope = scope) },
        drawerContent = {
            Column(modifier = Modifier.weight(1f).background(BlueGrayDark)) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 20.dp))
            }
            Column(modifier = Modifier.weight(3f)) {
                Drawer()
            }
        },
        drawerGesturesEnabled = true,
        floatingActionButton = { FloatingActionButton(onClick = { navigateTo(Screen.AddComicScreen) }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "add")
            }
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // TODO: Add homescreen content here or move into a separate function
        }
    }
}

@Composable
private fun Drawer() {
    val context = LocalContext.current
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
                    color = if (isSystemInDarkTheme()) Color.Gray else Color.Black,
                    thickness = 0.5.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
            }
        }
    }
}
