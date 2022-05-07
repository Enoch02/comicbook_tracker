package com.enoch2.comictracker.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.enoch2.comictracker.R
import com.enoch2.comictracker.router.BackButtonHandler
import com.enoch2.comictracker.router.Router
import com.enoch2.comictracker.router.Screen
import com.enoch2.comictracker.ui.theme.BlueGray400

@Composable
fun SettingScreen() {
    BackButtonHandler {
        Router.navigateTo(Screen.HomeScreen)
    }
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(stringResource(R.string.settings))
            },
                navigationIcon = {
                    IconButton(onClick = { Router.navigateTo(Screen.HomeScreen) }) {
                        Icon(Icons.Default.ArrowBack, "back")
                    }
                }
            )
        },
        content = {
            Surface(modifier = Modifier.fillMaxSize()) {
                Content()
            }
        }
    )
}

@Composable
private fun Content() {
    val constraints = ConstraintSet {
        val text = createRefFor("text")
        val checkBox = createRefFor("checkBox")
        val desc = createRefFor("desc")

        constrain(text) {
            start.linkTo(parent.start, 10.dp)
            top.linkTo(parent.top, 10.dp)
            width = Dimension.percent(0.9f)
        }
        constrain(checkBox) {
            top.linkTo(parent.top, 10.dp)
            end.linkTo(parent.end, 10.dp)
            bottom.linkTo(parent.bottom, 10.dp)
            width = Dimension.percent(0.1f)
        }
        constrain(desc) {
            top.linkTo(text.bottom)
            bottom.linkTo(parent.bottom, 10.dp)
            start.linkTo(parent.start, 15.dp)
        }
    }
    // TODO: Will load this from shared prefs
    var alwaysDark by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    // TODO: Save settings to shared prefs
    Column {
        ConstraintLayout(
            constraints,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    alwaysDark = !alwaysDark
                }
        ) {
            Text(
                stringResource(R.string.always_dark),
                fontSize = 20.sp,
                modifier = Modifier.layoutId("text")
            )
            Checkbox(
                checked = alwaysDark,
                onCheckedChange = { alwaysDark = it },
                modifier = Modifier.layoutId("checkBox")
            )
            Text(
                stringResource(R.string.always_dark_desc),
                fontSize = 12.sp,
                color = BlueGray400,
                modifier = Modifier.layoutId("desc")
            )
        }
        Divider()

        TextButton(
            onClick = { /*TODO*/ },
            content = { Text(stringResource(R.string.import_data)) },
            modifier = Modifier.fillMaxWidth()
        )
        Divider()

        TextButton(
            onClick = { /*TODO*/ },
            content = { Text(stringResource(R.string.export_data)) },
            modifier = Modifier.fillMaxWidth()
        )
        Divider()

        TextButton(
            onClick = { showDialog = !showDialog },
            content = { Text(stringResource(R.string.clear_data)) },
            modifier = Modifier.fillMaxWidth()
        )
        Divider()

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = !showDialog },
                title = { Text(stringResource(R.string.clear_data)) },
                text = { Text(stringResource(R.string.clear_data_dialog_body)) },
                confirmButton = {
                    Button(
                        onClick = { /*TODO*/ },
                        content = { Text(stringResource(R.string.yes)) }
                    )
                },
                dismissButton = {
                    Button(
                        onClick = { showDialog = !showDialog },
                        content = { Text(stringResource(R.string.no)) }
                    )
                }
            )
        }

        TextButton(
            onClick = {  },
            content = { Text("Do something") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview()
@Composable
fun SettingScreenPreview() {
    SettingScreen()
}