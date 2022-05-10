package com.enoch2.comictracker.ui.screen

import android.content.Context
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.enoch2.comictracker.R
import com.enoch2.comictracker.data.ComicDao
import com.enoch2.comictracker.ui.common_composables.ComicTrackerTopBar
import com.enoch2.comictracker.ui.theme.BlueGray400
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SettingScreen(navController: NavController, scope: CoroutineScope, comicDao: ComicDao) {
    Scaffold(
        topBar = {
            ComicTrackerTopBar(
                title = stringResource(R.string.settings),
                navIcon = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.back),
                onClick = { navController.popBackStack() }
            )
        },
        content = {
            Surface(modifier = Modifier.fillMaxSize()) {
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

                    if (showDialog) {
                        AlertDialog(
                            onDismissRequest = { showDialog = !showDialog },
                            title = { Text(stringResource(R.string.clear_data)) },
                            text = { Text(
                                stringResource(R.string.clear_data_dialog_body),
                                fontSize = 15.sp
                            ) },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        scope.launch {
                                            comicDao.deleteAll()
                                        }
                                        showDialog = !showDialog
                                    },
                                    content = { Text(stringResource(R.string.yes)) }
                                )
                            },
                            dismissButton = {
                                TextButton(
                                    onClick = { showDialog = !showDialog },
                                    content = { Text(stringResource(R.string.no)) }
                                )
                            }
                        )
                    }
                }
            }
        }
    )
}
