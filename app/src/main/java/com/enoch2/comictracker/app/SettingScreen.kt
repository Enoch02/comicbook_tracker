package com.enoch2.comictracker.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
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
        content = { Content() }
    )
}

@Composable
private fun Content() {
    val constraints = ConstraintSet {
        val text = createRefFor("text")
        val checkBox = createRefFor("checkBox")
        val desc = createRefFor("desc")

        constrain(text) {
            top.linkTo(parent.top)
            width = Dimension.percent(0.8f)
        }
        constrain(checkBox) {
            top.linkTo(parent.top)
            end.linkTo(parent.end)
            width = Dimension.percent(0.2f)
        }
        constrain(desc) {
            top.linkTo(text.bottom)
            start.linkTo(parent.start, margin = 5.dp)
        }
    }
    var alwaysDark by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    // TODO: Save settings to shared prefs
    Column(modifier = Modifier.padding(10.dp)) {
        ConstraintLayout(constraints, modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)) {
            Text(
                stringResource(R.string.always_dark),
                fontSize = 20.sp,
                modifier = Modifier.layoutId("text"),
            )
            Checkbox(
                checked = alwaysDark,
                onCheckedChange = { alwaysDark = it },
                modifier = Modifier.layoutId("checkBox"),
            )
            Text(
                stringResource(R.string.always_dark_desc),
                fontSize = 12.sp,
                color = BlueGray400,
                modifier = Modifier.layoutId("desc")
            )
        }
        MyDivider()
        ConstraintLayout(constraints, modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)) {

        }
        MyDivider()
        TextButton(
            onClick = { showDialog = !showDialog },
            content = { Text(stringResource(R.string.clear_data)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
        )
        MyDivider()
        TextButton(
            onClick = { /*TODO*/ },
            content = { Text(stringResource(R.string.export_data)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
        )
    }
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
}